package cn.coldcoder.service.Impl;

import cn.coldcoder.common.Const;
import cn.coldcoder.common.ResponseCode;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.dao.AccusationMapper;
import cn.coldcoder.dao.TopicCommentMapper;
import cn.coldcoder.dao.TopicMapper;
import cn.coldcoder.dao.UserMapper;
import cn.coldcoder.pojo.Topic;
import cn.coldcoder.service.ITopicService;
import cn.coldcoder.util.MsgSecCheckUtil;
import cn.coldcoder.util.propertiesUtil;
import cn.coldcoder.util.solrUtil;
import cn.coldcoder.vo.BasicTopicVo;
import cn.coldcoder.vo.TopicListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service("iTopicService")
public class TopicServiceImpl implements ITopicService {

    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccusationMapper accusationMapper;
    @Autowired
    private TopicCommentMapper topicCommentMapper;

    @RequestMapping("pulish_topic.do")
    @ResponseBody
    //发普通的帖子
    public ServerResponse<String> publishTopic(String uid, Topic topic){
        int reskStatus = MsgSecCheckUtil.MsgSecCheck(topic.getTitle()+topic.getContent());
        if(reskStatus>1){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.FAIL.getCode(),"内容有风险");
        }
        int intUid = Integer.parseInt(uid);
        //  检查分数

        Boolean isScoreEnough = userMapper.checkScore(intUid,Const.SCORE_TOPIC);
        if(isScoreEnough) {

            topic.setUid(intUid);
            //插入帖子信息
            int resultCount = topicMapper.insertSelective(topic);
            //更新分数信息
            int resultScore = userMapper.updateScore(intUid, Const.SCORE_TOPIC);
            if (resultCount > 0 && resultScore > 0) {
                try {
                    topic.setCreateTime(new Date());
                    solrUtil.addToSolr(propertiesUtil.getProperty("solr_topic_url"),topic);
                } catch (IOException e) {
                    e.printStackTrace();

                } catch (SolrServerException e) {
                    System.out.println("solrSeverException");
                    e.printStackTrace();
                }

                return ServerResponse.createBySuccessMessage("发布成功");
            } else {
                return ServerResponse.createByErrorMessage("发布失败");
            }
        }else {
            return ServerResponse.createBySuccessMessage("分数不够");
        }
    }

    public ServerResponse<String> deleteTopic(int topicId){
        int resultCount = topicMapper.deleteByPrimaryKey(topicId);
        if(resultCount > 0){
            topicCommentMapper.deleteByTopicId(topicId);
            try {
                solrUtil.deleteFromSolr(propertiesUtil.getProperty("solr_topic_url"),String.valueOf(topicId));
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

    //由帖子的类型按更新时间倒序来获取帖子列表
    //同时获取帖子的详细信息
    public ServerResponse<PageInfo> getTopicListOrderByUpdateTime(int pageNum, int pageSize,int topicType){
        PageHelper.startPage(pageNum,pageSize);
        List<TopicListVo> topicListVoList = topicMapper.selectTopicListByTypeOrderByUpdateTime(topicType);
        PageInfo PageResult = new PageInfo(topicListVoList);
        return ServerResponse.createBySuccess(PageResult);
    }

    public ServerResponse<PageInfo> getDeletedTopicList(int pageNum, int pageSize,int status){
        PageHelper.startPage(pageNum,pageSize);
        List<TopicListVo> topicListVoList = topicMapper.selectTopicListByStatus(status);
        PageInfo PageResult = new PageInfo(topicListVoList);
        return ServerResponse.createBySuccess(PageResult);
    }

    public ServerResponse<TopicListVo> getTopicVoById(int id){
        TopicListVo topicListVo = topicMapper.selectTopicVoById(id);

        if(StringUtils.isNotBlank(topicListVo.getId().toString())){
            return ServerResponse.createBySuccess(topicListVo);
        }else {
            return ServerResponse.createByErrorMessage("此贴已删除");
        }
    }

    public ServerResponse<String> reportTopic(Integer tid,String uid,String reason){
        int intUid = Integer.parseInt(uid);
        int checkResult = accusationMapper.checkByUidAndTid(tid,intUid,1);
        if(checkResult>0){
            return ServerResponse.createBySuccessMessage("已经举报过啦");
        }else{
            int resultCount = accusationMapper.insertByUidAndTid(tid,intUid,Const.TYPE_TOPIC,reason);
            if(resultCount>0){
                return ServerResponse.createBySuccessMessage("举报成功");
            }else {
                return ServerResponse.createByErrorMessage("操作失败");
            }
        }
    }

    //获取我发的所有帖子

    public ServerResponse<PageInfo> getMyTopicList(String uid,int pageNum,int pageSize){
        int intUid = Integer.parseInt(uid);
        PageHelper.startPage(pageNum,pageSize);
        List<BasicTopicVo> basicTopicVoList = topicMapper.selectBasicTopicByUid(intUid);
        PageInfo PageResult = new PageInfo(basicTopicVoList);
        return ServerResponse.createBySuccess(PageResult);
    }


    //按关键字查询帖子
    public ServerResponse<PageInfo> searchByKey(String key,int type,int pageNum,int pageSize ){
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(key)){
            key = new StringBuilder().append("%").append(key).append("%").toString();
        }
        List<BasicTopicVo> basicTopicVoList = topicMapper.searchTopicByKey(key,type);
        PageInfo PageResult = new PageInfo(basicTopicVoList);
        return ServerResponse.createBySuccess(PageResult);
    }

    //按照id更改帖子的状态，可删帖，可恢复
    public  ServerResponse<String> updateById(int id,int status){
        Topic topic = new Topic();
        topic.setId(id);
        topic.setStatus(status);
        int resultCount = topicMapper.updateByPrimaryKeySelective(topic);
        if(resultCount>0){
            topicCommentMapper.updateStatusByTopicId(id,status);
            try {
                if(status==3){
                    solrUtil.deleteFromSolr(propertiesUtil.getProperty("solr_topic_url"),String.valueOf(id));
                }else {
                    solrUtil.addToSolr(propertiesUtil.getProperty("solr_topic_url"),String.valueOf(id));
                }

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

    //根据用户的id查出所有状态的所有帖子
    public ServerResponse<PageInfo> selectAllTopicByUid(int uid,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Topic> topicList = topicMapper.selectAllTopicByuid(uid);
        PageInfo PageResult = new PageInfo(topicList);
        return ServerResponse.createBySuccess(PageResult);
    }

    //检查是否是该userId 的帖子，防止越权，暂未使用
    public Boolean isMyTopic(int uid,int topicId){
        return false;
    }

    public static void main(String[] args) {

    }
}
