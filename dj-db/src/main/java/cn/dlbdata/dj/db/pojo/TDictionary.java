package cn.dlbdata.dj.db.pojo;

import javax.persistence.*;

@Table(name = "t_dictionary")
public class TDictionary {
    @Id
    private Integer id;

    @Column(name = "item_code")
    private String itemCode;

    private String item;

    @Column(name = "category_code")
    private String categoryCode;

    private String category;

    /**
     * 数据字典的补充信息
     */
    private String supply;

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

    /**
     * @return item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * @param itemCode
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * @return item
     */
    public String getItem() {
        return item;
    }

    /**
     * @param item
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * @return category_code
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * @param categoryCode
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    /**
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 获取数据字典的补充信息
     *
     * @return supply - 数据字典的补充信息
     */
    public String getSupply() {
        return supply;
    }

    /**
     * 设置数据字典的补充信息
     *
     * @param supply 数据字典的补充信息
     */
    public void setSupply(String supply) {
        this.supply = supply;
    }
}