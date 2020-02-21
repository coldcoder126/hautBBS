package cn.coldcoder.controller.portal;

import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.Product;
import cn.coldcoder.service.IProductService;
import cn.coldcoder.util.cacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product/")
public class productController {
    @Autowired
    private IProductService iProductService;

    @RequestMapping("publish.do")
    @ResponseBody
    public ServerResponse publishProduct (@RequestHeader("Authorization") String token, Product product) {
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        }else {
            return iProductService.publishProduct(uid,product);
        }
    }

    @RequestMapping("delete_product.do")
    @ResponseBody
    public ServerResponse deleteProduct(@RequestHeader("Authorization") String token,int productId){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        }else {
            return iProductService.deleteProduct(productId);
        }
    }




    @RequestMapping("get_list.do")
    @ResponseBody
    //获取二手商品的列表，按照发布时间的先后排序
    public ServerResponse getList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return iProductService.getlist(pageNum,pageSize);
    }


    @RequestMapping("set_saled.do")
    @ResponseBody
    public ServerResponse setSaled(@RequestHeader("Authorization") String token,int productId){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        }else {
            return iProductService.setSaleStatus(uid,productId);
        }
    }

    @RequestMapping("get_ProductListByUid.do")
    @ResponseBody
    //查看我已发的商品
    public ServerResponse getMyProductList(@RequestHeader("Authorization") String token,
                                           @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                           @RequestParam(value = "UID", defaultValue = "0") int UID){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        }else {
            if(UID==0) {
                return iProductService.getMyProductList(uid, pageNum, pageSize);
            }else {
                uid = String.valueOf(UID);
                return iProductService.getMyProductList(uid, pageNum, pageSize);
            }
        }
    }

    @RequestMapping("get_commodityById.do")
    @ResponseBody
    public ServerResponse getProductById(@RequestHeader("Authorization") String token,int id){
        String uid = cacheUtil.getKey(token);
        if (StringUtils.isBlank(uid)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录");
        }else {
            return iProductService.getProductByid(id);
        }
    }

    @RequestMapping("search.do")
    @ResponseBody
//    按关键字搜索商品
    public ServerResponse searchByKey(String key,@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return iProductService.searchByKey(key,pageNum,pageSize);
    }
}
