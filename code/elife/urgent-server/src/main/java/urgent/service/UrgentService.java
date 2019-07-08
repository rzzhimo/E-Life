package urgent.service;


import net.minidev.json.JSONObject;

/**
 * UrgentService interface
 *
 * @author wyx
 * @date 2019.07.04
 */
public interface UrgentService {

    /**
     * save Urgent without time
     *
     * @param id
     * @param managerName
     * @param content
     * @param status
     */
    void save(int id, String managerName, String content, int status, int communityId);

    /**
     * find special Urgent with id
     *
     * @param id
     * @return JSONObject
     */
    JSONObject findOne(int id);

    /**
     * delete Urgent by Id
     *
     * @param id
     * @return boolean delete success or not
     */
    boolean deleteOne(int id);

}
