package payserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import payserver.entity.Pay;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * PayRepository interface
 *
 * @author wyx
 * @date 2019.07.12
 */
public interface PayRepository extends JpaRepository<Pay, Integer> {

    /**
     * save bill without id
     * @param time
     * @param status
     * @param bill
     * @param managerName
     * @param username
     * @param communityId
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value=" INSERT INTO pay(time,status,bill,managername,username,community_id) VALUES (?1,?2,?3,?4,?5,?6) ",nativeQuery = true)
    void savePay(String time, int status, BigDecimal bill, String managerName, String username, int communityId);


    /**
     * find newest Pay  by username
     *
     * @param username
     * @return List
     */
    @Query(value=" select * from pay where username=?1 and (status=-1 or status=-2)  order by id desc",nativeQuery = true)
    List<Pay> findNew(String username);

    /**
     * return history from  table
     *
     * @param username
     * @param left
     * @param right
     * @return List
     */
    @Query(value=" select * from pay where username=?1 order by id desc limit ?2,?3",nativeQuery = true)
    List<Pay> findHistory(String username,int left,int right);

    /**
     * return history which not taken
     *
     * @param communityId
     * @param left
     * @param right
     * @return List
     */
    @Query(value=" select * from pay where (status=-1 or status=-2 or status=-11 or status=-12) and community_id=?1 order by id desc limit ?2,?3",nativeQuery = true)
    List<Pay> findUnPayManager(int communityId,int left,int right);
    /**
     * return history which not taken
     *
     * @param communityId
     * @param left
     * @param right
     * @return List
     */
    @Query(value=" select * from pay where (status=1 or status=2) and community_id=?1 order by id desc limit ?2,?3",nativeQuery = true)
    List<Pay> findPayHistoryManager(int communityId,int left,int right);
    /**
     * get page num pay manager
     * @param communityId
     * @return
     */

    @Query(value="select * from pay  where  (status=1 or status=2) and community_id=?1",nativeQuery = true)
    List<Pay> findPageNumManager(int communityId);

    /**
     * get page num manager
     * @param communityId
     * @return
     */
    @Query(value="select * from pay  where  (status=-1 or status=-2) and community_id=?1",nativeQuery = true)
    List<Pay> findPageNumUnpayManager(int communityId);

    /**
     * get page num
     * @param username
     * @return
     */
    @Query(value=" select * from pay where username=?1 ",nativeQuery = true)
    List<Pay> findPageNum(String username);

    /**
     * get zfb Account
     * @param communityId
     * @return map zfb account
     */
    @Query(value=" select account from community where  id=?1 ",nativeQuery = true)
    Map<String,String> findAccount(int communityId);

}

