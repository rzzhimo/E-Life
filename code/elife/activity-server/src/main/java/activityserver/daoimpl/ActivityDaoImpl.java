package activityserver.daoimpl;

import activityserver.dao.ActivityDao;
import activityserver.entity.Activity;
import activityserver.entity.Participator;
import activityserver.repository.ActivityRepository;
import activityserver.repository.ParticipatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * ActivityDaoImpl class to implements Activity Dao
 *
 * @Author wyx
 * @Date 2019.07.11
 */
public class ActivityDaoImpl implements ActivityDao {
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    ParticipatorRepository participatorRepository;

    @Override
    public void saveActivity(String startTime, String endTime, String content,
                             String managerName, String title, int status, String photo, int communityId){
        activityRepository.saveActivity(startTime,endTime,content,managerName,title,status,photo,communityId);
    }

    @Override
    public List<Activity> findAllActivity(int communityId){
        return activityRepository.findAllActivity(communityId);
    }

    @Override
    public  List<Activity> findNewActivity(int communityId){
        return activityRepository.findNew(communityId);
    }

    @Override
    public void deleteActivity(int aid){
        activityRepository.deleteById(aid);
    }

    @Override
    public void changeStatusAct(int aid,int status){
        Activity activity =activityRepository.getOne(aid);
        activity.setStatus(status);
        activityRepository.save(activity);
    }

    @Override
    public void saveParticipator(int aid,String content,int status,String username){
        participatorRepository.savePaticipator(aid,content,status,username);
    }

    @Override
    public List<Participator> findAllParticipator(int aid){
        return participatorRepository.findAllParticipator(aid);
    }

    @Override
    public void changeStatusPar(int pid,int status){
        Participator participator =participatorRepository.getOne(pid);
        participator.setStatus(status);
        participatorRepository.save(participator);
    }

    @Override
    public void deleteAllDisPar(int aid){
        participatorRepository.deleteAll(aid);
    }

    @Override
    public boolean existApply(int aid,String username) {
        List<Participator> list = participatorRepository.existApply(aid, username);
        int size = list.size();
        if(size==0){
            return true;
        }
        else {
            return false;
        }

    }
}