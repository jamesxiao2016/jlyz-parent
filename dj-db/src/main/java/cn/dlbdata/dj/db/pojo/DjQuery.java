package cn.dlbdata.dj.db.pojo;

import javax.persistence.*;

@Table(name = "dj_query")
public class DjQuery {
    @Id
    private Integer id;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}