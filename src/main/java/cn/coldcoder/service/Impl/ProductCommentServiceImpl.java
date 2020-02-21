package cn.coldcoder.service.Impl;

import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.dao.AppreciationMapper;
import cn.coldcoder.dao.ProductCommentMapper;
import cn.coldcoder.dao.UserMapper;
import cn.coldcoder.pojo.ProductComment;
import cn.coldcoder.pojo.TopicComment;
import cn.coldcoder.service.IProductCommentService;
import cn.coldcoder.util.DateTimeUtil;
import cn.coldcoder.util.MsgSecCheckUtil;
import cn.coldcoder.vo.CommentVo;
import cn.coldcoder.vo.TopicCommentVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("iProductCommentService")
public class ProductCommentServiceImpl implements IProductCommentService {
    @Autowired
    private ProductCommentMapper productCommentMapper;
    @Autowired
    private AppreciationMapper appreciationMapper;
    @Autowired
    private UserMapper userMapper;

    public ServerResponse<PageInfo> getProductComment(int pageNum, int pageSize, String uid, int productId){
        PageHelper.startPage(pageNum,pageSize);
        Boolean isUidNull = StringUtils.isBlank(uid);
        int intUid = 0;
        List<Integer> appreciateProductCommentIdList = Lists.newArrayList();


        //1.查出该帖子下的所有一级评论   2.遍历从喜欢表中查出喜欢状态 3.遍历从用户表中查出基本信息     4..组装VO
        // 5.遍历一级评论，查找二级评论，查询喜欢状态，组装
        List<TopicCommentVo> topicCommentVoList = Lists.newArrayList();

        //查出帖子下的所有一级评论
        List<ProductComment> productComments = productCommentMapper.selectByProductId(productId);

        if(!isUidNull) {
            intUid = Integer.parseInt(uid);
            //一次查出该用户点赞过的所有评论id-List,在内存中查找是否当前评论id是否在该List中以减少数据库访问次数
            appreciateProductCommentIdList = appreciationMapper.selectAllTiddByUid(intUid, 4);
        }
        //遍历一级评论
        for(ProductComment productComment:productComments){
            //1.查喜欢状态2.查用户信息3.组装一下
            TopicCommentVo topicCommentVo = new TopicCommentVo();

            topicCommentVo.setId(productComment.getId());
            topicCommentVo.setContent(productComment.getContent());
            topicCommentVo.setCreateTime(productComment.getCreateTime());
            topicCommentVo.setLevel(productComment.getLevel());
            topicCommentVo.setParentId(productComment.getParentId());
            topicCommentVo.setFromUser(userMapper.selectBasicByKey(productComment.getFromUid()));
            topicCommentVo.setLike(appreciateProductCommentIdList.contains(productComment.getId()));    //List为空的时候此方法会返回false
            topicCommentVo.setChildTopicCommentVoList(getChildCommentByParentId(productComment.getId(), appreciateProductCommentIdList));
            topicCommentVoList.add(topicCommentVo);
        }
        PageInfo pageResult = new PageInfo(productComments);
        pageResult.setList(topicCommentVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    public ServerResponse publishComment(ProductComment productComment){
        int reskStatus = MsgSecCheckUtil.MsgSecCheck(productComment.getContent());
        if(reskStatus>1){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.FAIL.getCode(),"内容有风险");
        }
        Boolean isEnough = userMapper.checkScore(productComment.getFromUid(),1);
        if(isEnough) {  //服务器再验证一次分数够不够
        int resultCount = productCommentMapper.insertSelective(productComment);
            if(resultCount>0){
                return ServerResponse.createBySuccessMessage("发表成功");
            }else {
                return ServerResponse.createByErrorMessage("发表失败");
            }

        }else {
            return ServerResponse.createByErrorMessage("分数不够");
        }

    }

    public ServerResponse<String> deleteComment(int commentId,int from_uid){
        int resultCount = productCommentMapper.updateStatus(commentId,from_uid);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("删除成功");
        }else {
            return ServerResponse.createByErrorMessage("操作失败");
        }
    }

    public ServerResponse<PageInfo> getNotification(int pageNum,int pageSize,String uid){
        PageHelper.startPage(pageNum,pageSize);
        int intUid = Integer.parseInt(uid);
        //获取所有的 to_uid 为“我”的评论
        List<CommentVo> commentVoList = Lists.newArrayList();
        List<ProductComment> productComments = productCommentMapper.selectByToUid(intUid);
        for(ProductComment productComment:productComments){
            CommentVo commentVo = new CommentVo();

            commentVo.setId(productComment.getId());
            commentVo.setTopicId(productComment.getProductId());
            commentVo.setContent(productComment.getContent());
            commentVo.setHaveRead(productComment.getHaveRead());
            commentVo.setCreateTime(productComment.getCreateTime());
            commentVo.setUpdateTime(productComment.getUpdateTime());
            commentVo.setStatus((productComment.getStatus()));
            commentVo.setFromUser(userMapper.selectBasicByKey(productComment.getFromUid()));

            commentVoList.add(commentVo);
        }
        PageInfo pageResult = new PageInfo(productComments);
        pageResult.setList(commentVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    public ServerResponse readAll(String uid){
        int intUid = Integer.parseInt(uid);
        int resultCount = productCommentMapper.readAll(intUid);
        return ServerResponse.createBySuccessMessage("全部已读");
    }


    private List<TopicCommentVo> getChildCommentByParentId(Integer parentId, List<Integer> LikedCommentIds){
        List<TopicCommentVo> childProductCommentVos = Lists.newArrayList();
        List<ProductComment> productComments = productCommentMapper.selectChildByParentId(parentId);
        //由parentId查出其下的所有二级评论，然后组装
        for(ProductComment productComment:productComments){
            TopicCommentVo topicCommentVo = new TopicCommentVo();
            topicCommentVo.setId(productComment.getId());
            topicCommentVo.setContent(productComment.getContent());
            topicCommentVo.setLike(LikedCommentIds.contains(productComment.getId()));
            topicCommentVo.setLevel(productComment.getLevel());
            topicCommentVo.setParentId(productComment.getParentId());
            topicCommentVo.setFromUser(userMapper.selectBasicByKey(productComment.getFromUid()));
            topicCommentVo.setToUser(userMapper.selectBasicByKey(productComment.getToUid()));
            topicCommentVo.setCreateTime(productComment.getCreateTime());
            childProductCommentVos.add(topicCommentVo);
        }
        return childProductCommentVos;
    }
}
