package user.service;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import user.dao.FriendDao;
import user.dao.UserDao;
import user.entity.Community;
import user.entity.Friend;
import user.entity.User;
import user.repository.CommunityRepository;
import java.util.HashMap;
import java.util.List;

/**
 * @author ztHou
 */
@Service
public class FriendServiceImpl implements FriendService {

    private final UserDao userDao;
    private final FriendDao friendDao;
    private final CommunityRepository communityRepository;

    public FriendServiceImpl(UserDao userDao, FriendDao friendDao, CommunityRepository communityRepository) {
        this.userDao = userDao;
        this.friendDao = friendDao;
        this.communityRepository = communityRepository;
    }

    @Override
    public JSONArray friendSearchList(String username){
        JSONArray result = new JSONArray();
        List<User> userList = userDao.findAllByUsernameContains(username);
        HashMap<Long, String>communityInfo = new HashMap<>();
        List<Community> communityList = communityRepository.findAll();
        for(Community community : communityList){
            communityInfo.put(community.getId(), community.getCommunityName());
        }
        for(User user : userList){
            JSONObject object = new JSONObject();
            object.put("username", user.getUsername());
            object.put("community", communityInfo.get(user.getCommunityId()));
            result.appendElement(object);
        }
        return result;
    }

    @Override
    public JSONObject sendFriendRequest(String username, String friend, String content){
        JSONObject result = new JSONObject();
        result.put("send", 0);
        if(username.equals(friend)){
            return result;
        }else if(friendDao.existByUsernameAndFriendAndStatus(username, friend, 1)){
            return result;
        }
        try{
            Friend friendInfo = new Friend(username, friend, 0, content);
            friendDao.save(friendInfo);
            result.put("send", 1);
            return result;
        }catch (Exception e){
            return result;
        }
    }

    @Override
    public JSONArray friendList(String username){
        JSONArray result = new JSONArray();
        List<Friend> friendList = friendDao.allFriends(username);
        HashMap<Long, String>communityInfo = new HashMap<>();
        List<Community> communityList = communityRepository.findAll();
        for(Community community : communityList){
            communityInfo.put(community.getId(), community.getCommunityName());
        }
        for(Friend friend : friendList){
            JSONObject object = new JSONObject();
            String friendName = friend.getFriend();
            User user = userDao.findByUsername(friendName);
            object.put("friend", friendName);
            object.put("phone", user.getPhone());
            object.put("email", user.getEmail());
            object.put("community", communityInfo.get(user.getCommunityId()));
            result.appendElement(object);
        }
        return result;
    }

    @Override
    public JSONArray requestList(String username){
        JSONArray result = new JSONArray();
        List<Friend> friendList = friendDao.allRequests(username);
        for(Friend friend : friendList){
            JSONObject object = new JSONObject();
            object.put("id", friend.getId());
            object.put("request", friend.getFriend());
            object.put("content", friend.getContent());
            object.put("status", friend.getStatus());
            result.appendElement(object);
        }
        return result;
    }

    @Override
    public JSONArray responseList(String username){
        JSONArray result = new JSONArray();
        List<Friend> friendList = friendDao.allResponses(username);
        for(Friend friend : friendList){
            JSONObject object = new JSONObject();
            object.put("id", friend.getId());
            object.put("request", friend.getUsername());
            object.put("content", friend.getContent());
            result.appendElement(object);
        }
        return result;
    }

    @Override
    public JSONObject acceptRequest(Long id){
        JSONObject result = new JSONObject();
        result.put("accept", 1);
        if(!friendDao.existsById(id)){
            result.put("accept", 0);
            return result;
        }
        Friend friendInfo = friendDao.findById(id);
        if(friendInfo.getStatus() == 1){
            return result;
        }
        String username = friendInfo.getUsername();
        String friend = friendInfo.getFriend();
        try{
            friendDao.deleteByUsernameAndFriend(username, friend);
            Friend friendInfo1 = new Friend(username, friend, 1, "");
            friendDao.save(friendInfo1);
            Friend friendInfo2 = new Friend(friend, username, 1, "");
            friendDao.save(friendInfo2);
            return result;
        }catch (Exception e){
            result.put("accept", 0);
            return result;
        }
    }

    @Override
    public JSONObject rejectRequest(Long id){
        JSONObject result = new JSONObject();
        result.put("reject", 0);
        if(!friendDao.existsById(id)){
            return result;
        }
        Friend friendInfo = friendDao.findById(id);
        if(friendInfo.getStatus() == 1){
            return result;
        }
        friendInfo.setStatus(-1);
        friendDao.save(friendInfo);
        result.put("reject", 1);
        return result;
    }

    @Override
    public JSONObject deleteFriend(String username, String friend){
        JSONObject object = new JSONObject();
        object.put("delete", 0);
        try{
            friendDao.deleteByUsernameAndFriend(username, friend);
            friendDao.deleteByUsernameAndFriend(friend, username);
            object.put("delete", 1);
            return object;
        }catch (Exception e){
            return object;
        }
    }
}
