package cn.coldcoder.service.Impl;

import cn.coldcoder.common.Const;
import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.dao.ProductCommentMapper;
import cn.coldcoder.dao.ProductMapper;
import cn.coldcoder.dao.UserMapper;
import cn.coldcoder.pojo.Product;
import cn.coldcoder.service.IProductService;
import cn.coldcoder.util.MsgSecCheckUtil;
import cn.coldcoder.util.propertiesUtil;
import cn.coldcoder.util.solrUtil;
import cn.coldcoder.vo.ProductListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductCommentMapper productCommentMapper;

    //发布一条商品的实现
    public ServerResponse publishProduct(String uid,Product product){
        int reskStatus = MsgSecCheckUtil.MsgSecCheck(product.getTitle()+product.getDescription());
        if(reskStatus>1){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.FAIL.getCode(),"内容有风险");
        }
        int intUid = Integer.parseInt(uid);
        Boolean isScoreEnough = userMapper.checkScore(intUid,Const.SCORE_TOPIC);
        if(isScoreEnough) {
            product.setUid(intUid);
            int resultCount = productMapper.insertSelective(product);
            int resultScore = userMapper.updateScore(intUid, Const.SCORE_TOPIC);
            if (resultCount > 0 && resultScore > 0) {
                try {
                    product.setCreateTime(new Date());

                    solrUtil.addToSolr(propertiesUtil.getProperty("solr_product_url"),product);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SolrServerException e) {
                    e.printStackTrace();
                }
                return ServerResponse.createBySuccessMessage("发布成功");
            } else {
                return ServerResponse.createBySuccessMessage("发布失败");
            }
        }else {
            return ServerResponse.createByErrorMessage("分数不够");
        }
    }


    //显示当前商品列表
    public ServerResponse<PageInfo> getlist(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<ProductListVo> productListVoList = productMapper.selectOrderByCreateTime();
        PageInfo PageResult = new PageInfo(productListVoList);
        return ServerResponse.createBySuccess(PageResult);

    }

    public ServerResponse<PageInfo> getDeletedList(int pageNum, int pageSize,int status){
        PageHelper.startPage(pageNum,pageSize);
        List<ProductListVo> productListVoList = productMapper.selectDeletedList(status);
        PageInfo PageResult = new PageInfo(productListVoList);
        return ServerResponse.createBySuccess(PageResult);
    }


    //将一个商品的状态置为已售出，不可逆
    public ServerResponse setSaleStatus(String uid,Integer productId){
        int intUid = Integer.parseInt(uid);
        //1.检查商品的状态，为1则返回“已为售出状态”，否则可以置为已售
        int resultCount = productMapper.updateSaledStatus(productId,intUid);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("更改成功");
        }else if(resultCount == 0){
            return ServerResponse.createByErrorMessage("不可重复设置");
        }else{
            return ServerResponse.createByErrorMessage("更改失败");
        }
    }

    //查看我已发布的商品
    public ServerResponse getMyProductList(String uid,int pageNum, int pageSize){
        int intUid = Integer.parseInt(uid);
        PageHelper.startPage(pageNum,pageSize);
        List<ProductListVo> productListVoList = productMapper.selectProductListByUid(intUid);
        PageInfo PageResult = new PageInfo(productListVoList);
        return ServerResponse.createBySuccess(PageResult);
    }

    //按关键字模糊查询商品
    public ServerResponse<PageInfo> searchByKey(String key,int pageNum,int pageSize ){
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(key)){
            key = new StringBuilder().append("%").append(key).append("%").toString();
        }
        List<ProductListVo> productListVoList = productMapper.searchProductByKey(key);
        PageInfo PageResult = new PageInfo(productListVoList);
        return ServerResponse.createBySuccess(PageResult);
    }

    public ServerResponse updateStatusById(int id,int status){
        Product product = new Product();
        product.setId(id);
        product.setStatus(status);
        int resultCount = productMapper.updateByPrimaryKeySelective(product);
        if(resultCount>0){
            productCommentMapper.updateStatusByProductId(id,status);

            try {
                if(status==3)
                solrUtil.deleteFromSolr(propertiesUtil.getProperty("solr_product_url"),String.valueOf(id));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SolrServerException e) {
                e.printStackTrace();
            }
            return ServerResponse.createBySuccessMessage("更改成功");
        }else {
            return ServerResponse.createByErrorMessage("更改失败");
        }
    }

    public ServerResponse deleteProduct(int ProductId){
        int resultCount = productMapper.deleteByPrimaryKey(ProductId);
        if (resultCount>0){
            productCommentMapper.deleteByProductId(ProductId);
            try {
                solrUtil.deleteFromSolr(propertiesUtil.getProperty("solr_product_url"),String.valueOf(ProductId));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SolrServerException e) {
                e.printStackTrace();
            }
            return ServerResponse.createBySuccessMessage("已删除");
        }else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }

    public ServerResponse<PageInfo> selectAllProductByUid(int uid,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectAllProductByuid(uid);
        PageInfo PageResult = new PageInfo(productList);
        return ServerResponse.createBySuccess(PageResult);
    }

    public ServerResponse getProductByid(int id){
        ProductListVo productListVo = productMapper.selectByKeyAndStatus(id);
        if(StringUtils.isNotBlank(productListVo.getId().toString())){
            return ServerResponse.createBySuccess(productListVo);
        }else {
            return ServerResponse.createByErrorMessage("此贴已删除");
        }

    }

    }
