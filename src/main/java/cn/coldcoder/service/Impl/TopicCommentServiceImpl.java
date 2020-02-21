package cn.coldcoder.service.Impl;

import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.dao.AppreciationMapper;
import cn.coldcoder.dao.TopicCommentMapper;
import cn.coldcoder.dao.UserMapper;
import cn.coldcoder.pojo.TopicComment;
import cn.coldcoder.service.ITopicCommentService;
import cn.coldcoder.util.DateTimeUtil;
import cn.coldcoder.util.MsgSecCheckUtil;
import cn.coldcoder.vo.CommentVo;
import cn.coldcoder.vo.TopicCommentVo;
import cn.coldcoder.vo.TopicListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service("iTopicCommentService")
public class TopicCommentServiceImpl implements ITopicCommentService {

    @Autowired
    private TopicCommentMapper topicCommentMapper;
    @Autowired
    private AppreciationMapper appreciationMapper;
    @Autowired
    private UserMapper userMapper;


    /**
     *
     * @param pageNum   页数
     * @param pageSize  每页容量
     * @param uid       客户端登陆账号的id
     * @param topicId   正在浏览帖子的id
     * @return          封装好的PageInfo
     * 附加条件：用户未登录也要能看
     */
    //传入topicId获取关于这个帖子的评论
    public ServerResponse<PageInfo> getTopicComment(int pageNum,int pageSize,String uid,int topicId){
        PageHelper.startPage(pageNum,pageSize);
        Boolean isUidNull = StringUtils.isBlank(uid);
        int intUid = 0;
        List<Integer> appreciateTopicCommentIdList = Lists.newArrayList();


        //1.查出该帖子下的所有一级评论   2.遍历从喜欢表中查出喜欢状态 3.遍历从用户表中查出基本信息     4..组装VO
        // 5.遍历一级评论，查找二级评论，查询喜欢状态，组装
        List<TopicCommentVo> topicCommentVoList = Lists.newArrayList();

        //查出帖子下的所有一级评论
        List<TopicComment> topicComments = topicCommentMapper.selectByTopicId(topicId);

        if(!isUidNull) {
            intUid = Integer.parseInt(uid);
            //一次查出该用户点赞过的所有评论id-List,在内存中查找是否当前评论id是否在该List中以减少数据库访问次数
            appreciateTopicCommentIdList = appreciationMapper.selectAllTiddByUid(intUid, 2);
        }
        //遍历一级评论
        for(TopicComment topicComment:topicComments){
            //1.查喜欢状态2.查用户信息3.组装一下
            TopicCommentVo topicCommentVo = new TopicCommentVo();

            topicCommentVo.setId(topicComment.getId());
            topicCommentVo.setContent(topicComment.getContent());
            topicCommentVo.setCreateTime(topicComment.getCreateTime());
            topicCommentVo.setLevel(topicComment.getLevel());
            topicCommentVo.setParentId(topicComment.getParentId());
            topicCommentVo.setFromUser(userMapper.selectBasicByKey(topicComment.getFromUid()));
            topicCommentVo.setLike(appreciateTopicCommentIdList.contains(topicComment.getId()));    //List为空的时候此方法会返回false
            topicCommentVo.setChildTopicCommentVoList(getChildCommentByParentId(topicComment.getId(), appreciateTopicCommentIdList));
            topicCommentVoList.add(topicCommentVo);
        }
        PageInfo pageResult = new PageInfo(topicComments);
        pageResult.setList(topicCommentVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    //获取二级评论内容并组装

    /**
     * @param parentId 父id,据此来查该父Id下的所有二级评论
     * @param LikedCommentIds  喜欢Id的list,看该list中是否包含查出的id
     * @return
     */
    private List<TopicCommentVo> getChildCommentByParentId(Integer parentId,List<Integer> LikedCommentIds){
        List<TopicCommentVo> childTopicCommentVos = Lists.newArrayList();
        List<TopicComment> topicComments = topicCommentMapper.selectChildByParentId(parentId);
        //由parentId查出其下的所有二级评论，然后组装
        for(TopicComment topicComment:topicComments){
            TopicCommentVo topicCommentVo = new TopicCommentVo();
            topicCommentVo.setId(topicComment.getId());
            topicCommentVo.setContent(topicComment.getContent());
            topicCommentVo.setLike(LikedCommentIds.contains(topicComment.getId()));
            topicCommentVo.setLevel(topicComment.getLevel());
            topicCommentVo.setParentId(topicComment.getParentId());
            topicCommentVo.setFromUser(userMapper.selectBasicByKey(topicComment.getFromUid()));
            topicCommentVo.setToUser(userMapper.selectBasicByKey(topicComment.getToUid()));

                topicCommentVo.setCreateTime(topicComment.getCreateTime());
            childTopicCommentVos.add(topicCommentVo);
        }
        return childTopicCommentVos;
    }


    public ServerResponse<String> publishComment(TopicComment topicComment){
        //1.检查该用户分数是否足够，不够返回分数不够 2.分数足够则提交一条评论，触发器会更新帖子表，执行存储过程更新更新分数

        int reskStatus = MsgSecCheckUtil.MsgSecCheck(topicComment.getContent());
        if(reskStatus>1){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.FAIL.getCode(),"内容有风险");
        }
        Boolean isEnough = userMapper.checkScore(topicComment.getFromUid(),1);
        if(isEnough){  //服务器再验证一次分数够不够
            int resultCount = topicCommentMapper.insertSelective(topicComment);
            if(resultCount>0){
                return ServerResponse.createBySuccessMessage("发表成功");
            }else {
                return ServerResponse.createByErrorMessage("发表失败");
            }
        }else {
            return ServerResponse.createByErrorMessage("分数不够");
        }
    }

    public ServerResponse<PageInfo> getNotification(int pageNum,int pageSize,String uid){
        PageHelper.startPage(pageNum,pageSize);
        int intUid = Integer.parseInt(uid);
        //获取所有的 to_uid 为“我”的评论
        List<CommentVo> commentVoList = Lists.newArrayList();
        List<TopicComment> topicComments = topicCommentMapper.selectByToUid(intUid);

        for(TopicComment topicComment:topicComments){
            CommentVo commentVo = new CommentVo();

            commentVo.setId(topicComment.getId());
            commentVo.setTopicId(topicComment.getTopicId());
            commentVo.setContent(topicComment.getContent());
            commentVo.setHaveRead(topicComment.getHaveRead());
            commentVo.setStatus(topicComment.getStatus());
            commentVo.setCreateTime(topicComment.getCreateTime());
            commentVo.setUpdateTime(topicComment.getUpdateTime());
            commentVo.setFromUser(userMapper.selectBasicByKey(topicComment.getFromUid()));
            commentVoList.add(commentVo);
        }
        PageInfo pageResult = new PageInfo(topicComments);
        pageResult.setList(commentVoList);
        return ServerResponse.createBySuccess(pageResult);

    }

    public ServerResponse deleteComment(int commentId,int from_uid){
        //验证是否评论者的id是否和传入id一致，防止越权发生
        int resultCount = topicCommentMapper.updateStatus(commentId,from_uid);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("删除成功");
        }else {
            return ServerResponse.createByErrorMessage("操作失败");
        }
    }

    public ServerResponse readAll(String uid){
        int intUid = Integer.parseInt(uid);
        int resultCount = topicCommentMapper.readAll(intUid);
        return ServerResponse.createBySuccessMessage("全部已读");
    }


    //获取我所发布的评论(未删除的)
    public ServerResponse<PageInfo> getMyComments(int pageNum,int pageSize,String uid){
        int intUid = Integer.parseInt(uid);
        PageHelper.startPage(pageNum,pageSize);
        List<CommentVo> commentVoList = Lists.newArrayList();

        List<TopicComment> topicComments = topicCommentMapper.selectByFromUid(intUid);
        for(TopicComment topicComment:topicComments){
            CommentVo commentVo = new CommentVo();

            commentVo.setId(topicComment.getId());
            commentVo.setTopicId(commentVo.getTopicId());
            commentVo.setContent(topicComment.getContent());
            commentVo.setCreateTime(topicComment.getCreateTime());
            commentVo.setFromUser(userMapper.selectBasicByKey(topicComment.getToUid()));

            commentVoList.add(commentVo);
        }
        PageInfo pageResult = new PageInfo(topicComments);
        pageResult.setList(commentVoList);
        return ServerResponse.createBySuccess(pageResult);
    }


}
