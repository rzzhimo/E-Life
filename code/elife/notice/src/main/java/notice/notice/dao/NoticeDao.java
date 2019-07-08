package notice.notice.dao;

import notice.notice.entity.Notice;

import java.util.List;

/**
 * postComments class
 *
 * @author 符永锐
 * @date 2019/07/05*/
public interface NoticeDao {
    /**
     * 该函数增加一条物业通知
     * @return 返回生成的物业通知的id
     * @param  notice 要存的物业通知*/
    int save(Notice notice);
    /**
     * 该函数查询特定用户的物业通知
     * @return 返回特定用户的物业通知的id
     * @param  username 用户名*/
    List<Notice> findByUsername(String username);
    /**
     * 该函数删除特定用户的所有物业通知
     * @param username 用户名*/
    void deleteAllByUsername(String username);
    /**
     * 该函数删除特定一条物业通知
     * @param NoticeId 物业通知id*/
    void deleteAllByNotcieId(int NoticeId);
}
