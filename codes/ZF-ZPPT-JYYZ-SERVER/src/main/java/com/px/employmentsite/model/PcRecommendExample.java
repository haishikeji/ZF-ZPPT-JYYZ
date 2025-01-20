package com.px.employmentsite.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PcRecommendExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcRecommendExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andRecommendIDIsNull() {
            addCriterion("RecommendID is null");
            return (Criteria) this;
        }

        public Criteria andRecommendIDIsNotNull() {
            addCriterion("RecommendID is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendIDEqualTo(String value) {
            addCriterion("RecommendID =", value, "recommendID");
            return (Criteria) this;
        }

        public Criteria andRecommendIDNotEqualTo(String value) {
            addCriterion("RecommendID <>", value, "recommendID");
            return (Criteria) this;
        }

        public Criteria andRecommendIDGreaterThan(String value) {
            addCriterion("RecommendID >", value, "recommendID");
            return (Criteria) this;
        }

        public Criteria andRecommendIDGreaterThanOrEqualTo(String value) {
            addCriterion("RecommendID >=", value, "recommendID");
            return (Criteria) this;
        }

        public Criteria andRecommendIDLessThan(String value) {
            addCriterion("RecommendID <", value, "recommendID");
            return (Criteria) this;
        }

        public Criteria andRecommendIDLessThanOrEqualTo(String value) {
            addCriterion("RecommendID <=", value, "recommendID");
            return (Criteria) this;
        }

        public Criteria andRecommendIDLike(String value) {
            addCriterion("RecommendID like", value, "recommendID");
            return (Criteria) this;
        }

        public Criteria andRecommendIDNotLike(String value) {
            addCriterion("RecommendID not like", value, "recommendID");
            return (Criteria) this;
        }

        public Criteria andRecommendIDIn(List<String> values) {
            addCriterion("RecommendID in", values, "recommendID");
            return (Criteria) this;
        }

        public Criteria andRecommendIDNotIn(List<String> values) {
            addCriterion("RecommendID not in", values, "recommendID");
            return (Criteria) this;
        }

        public Criteria andRecommendIDBetween(String value1, String value2) {
            addCriterion("RecommendID between", value1, value2, "recommendID");
            return (Criteria) this;
        }

        public Criteria andRecommendIDNotBetween(String value1, String value2) {
            addCriterion("RecommendID not between", value1, value2, "recommendID");
            return (Criteria) this;
        }

        public Criteria andPostIDIsNull() {
            addCriterion("PostID is null");
            return (Criteria) this;
        }

        public Criteria andPostIDIsNotNull() {
            addCriterion("PostID is not null");
            return (Criteria) this;
        }

        public Criteria andPostIDEqualTo(String value) {
            addCriterion("PostID =", value, "postID");
            return (Criteria) this;
        }

        public Criteria andPostIDNotEqualTo(String value) {
            addCriterion("PostID <>", value, "postID");
            return (Criteria) this;
        }

        public Criteria andPostIDGreaterThan(String value) {
            addCriterion("PostID >", value, "postID");
            return (Criteria) this;
        }

        public Criteria andPostIDGreaterThanOrEqualTo(String value) {
            addCriterion("PostID >=", value, "postID");
            return (Criteria) this;
        }

        public Criteria andPostIDLessThan(String value) {
            addCriterion("PostID <", value, "postID");
            return (Criteria) this;
        }

        public Criteria andPostIDLessThanOrEqualTo(String value) {
            addCriterion("PostID <=", value, "postID");
            return (Criteria) this;
        }

        public Criteria andPostIDLike(String value) {
            addCriterion("PostID like", value, "postID");
            return (Criteria) this;
        }

        public Criteria andPostIDNotLike(String value) {
            addCriterion("PostID not like", value, "postID");
            return (Criteria) this;
        }

        public Criteria andPostIDIn(List<String> values) {
            addCriterion("PostID in", values, "postID");
            return (Criteria) this;
        }

        public Criteria andPostIDNotIn(List<String> values) {
            addCriterion("PostID not in", values, "postID");
            return (Criteria) this;
        }

        public Criteria andPostIDBetween(String value1, String value2) {
            addCriterion("PostID between", value1, value2, "postID");
            return (Criteria) this;
        }

        public Criteria andPostIDNotBetween(String value1, String value2) {
            addCriterion("PostID not between", value1, value2, "postID");
            return (Criteria) this;
        }

        public Criteria andJobuserIDIsNull() {
            addCriterion("JobuserID is null");
            return (Criteria) this;
        }

        public Criteria andJobuserIDIsNotNull() {
            addCriterion("JobuserID is not null");
            return (Criteria) this;
        }

        public Criteria andJobuserIDEqualTo(String value) {
            addCriterion("JobuserID =", value, "jobuserID");
            return (Criteria) this;
        }

        public Criteria andJobuserIDNotEqualTo(String value) {
            addCriterion("JobuserID <>", value, "jobuserID");
            return (Criteria) this;
        }

        public Criteria andJobuserIDGreaterThan(String value) {
            addCriterion("JobuserID >", value, "jobuserID");
            return (Criteria) this;
        }

        public Criteria andJobuserIDGreaterThanOrEqualTo(String value) {
            addCriterion("JobuserID >=", value, "jobuserID");
            return (Criteria) this;
        }

        public Criteria andJobuserIDLessThan(String value) {
            addCriterion("JobuserID <", value, "jobuserID");
            return (Criteria) this;
        }

        public Criteria andJobuserIDLessThanOrEqualTo(String value) {
            addCriterion("JobuserID <=", value, "jobuserID");
            return (Criteria) this;
        }

        public Criteria andJobuserIDLike(String value) {
            addCriterion("JobuserID like", value, "jobuserID");
            return (Criteria) this;
        }

        public Criteria andJobuserIDNotLike(String value) {
            addCriterion("JobuserID not like", value, "jobuserID");
            return (Criteria) this;
        }

        public Criteria andJobuserIDIn(List<String> values) {
            addCriterion("JobuserID in", values, "jobuserID");
            return (Criteria) this;
        }

        public Criteria andJobuserIDNotIn(List<String> values) {
            addCriterion("JobuserID not in", values, "jobuserID");
            return (Criteria) this;
        }

        public Criteria andJobuserIDBetween(String value1, String value2) {
            addCriterion("JobuserID between", value1, value2, "jobuserID");
            return (Criteria) this;
        }

        public Criteria andJobuserIDNotBetween(String value1, String value2) {
            addCriterion("JobuserID not between", value1, value2, "jobuserID");
            return (Criteria) this;
        }

        public Criteria andIsReadIsNull() {
            addCriterion("IsRead is null");
            return (Criteria) this;
        }

        public Criteria andIsReadIsNotNull() {
            addCriterion("IsRead is not null");
            return (Criteria) this;
        }

        public Criteria andIsReadEqualTo(Boolean value) {
            addCriterion("IsRead =", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadNotEqualTo(Boolean value) {
            addCriterion("IsRead <>", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadGreaterThan(Boolean value) {
            addCriterion("IsRead >", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadGreaterThanOrEqualTo(Boolean value) {
            addCriterion("IsRead >=", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadLessThan(Boolean value) {
            addCriterion("IsRead <", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadLessThanOrEqualTo(Boolean value) {
            addCriterion("IsRead <=", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadIn(List<Boolean> values) {
            addCriterion("IsRead in", values, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadNotIn(List<Boolean> values) {
            addCriterion("IsRead not in", values, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadBetween(Boolean value1, Boolean value2) {
            addCriterion("IsRead between", value1, value2, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadNotBetween(Boolean value1, Boolean value2) {
            addCriterion("IsRead not between", value1, value2, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsSendnotesIsNull() {
            addCriterion("IsSendnotes is null");
            return (Criteria) this;
        }

        public Criteria andIsSendnotesIsNotNull() {
            addCriterion("IsSendnotes is not null");
            return (Criteria) this;
        }

        public Criteria andIsSendnotesEqualTo(Boolean value) {
            addCriterion("IsSendnotes =", value, "isSendnotes");
            return (Criteria) this;
        }

        public Criteria andIsSendnotesNotEqualTo(Boolean value) {
            addCriterion("IsSendnotes <>", value, "isSendnotes");
            return (Criteria) this;
        }

        public Criteria andIsSendnotesGreaterThan(Boolean value) {
            addCriterion("IsSendnotes >", value, "isSendnotes");
            return (Criteria) this;
        }

        public Criteria andIsSendnotesGreaterThanOrEqualTo(Boolean value) {
            addCriterion("IsSendnotes >=", value, "isSendnotes");
            return (Criteria) this;
        }

        public Criteria andIsSendnotesLessThan(Boolean value) {
            addCriterion("IsSendnotes <", value, "isSendnotes");
            return (Criteria) this;
        }

        public Criteria andIsSendnotesLessThanOrEqualTo(Boolean value) {
            addCriterion("IsSendnotes <=", value, "isSendnotes");
            return (Criteria) this;
        }

        public Criteria andIsSendnotesIn(List<Boolean> values) {
            addCriterion("IsSendnotes in", values, "isSendnotes");
            return (Criteria) this;
        }

        public Criteria andIsSendnotesNotIn(List<Boolean> values) {
            addCriterion("IsSendnotes not in", values, "isSendnotes");
            return (Criteria) this;
        }

        public Criteria andIsSendnotesBetween(Boolean value1, Boolean value2) {
            addCriterion("IsSendnotes between", value1, value2, "isSendnotes");
            return (Criteria) this;
        }

        public Criteria andIsSendnotesNotBetween(Boolean value1, Boolean value2) {
            addCriterion("IsSendnotes not between", value1, value2, "isSendnotes");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDIsNull() {
            addCriterion("CreateUserID is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDIsNotNull() {
            addCriterion("CreateUserID is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDEqualTo(String value) {
            addCriterion("CreateUserID =", value, "createUserID");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDNotEqualTo(String value) {
            addCriterion("CreateUserID <>", value, "createUserID");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDGreaterThan(String value) {
            addCriterion("CreateUserID >", value, "createUserID");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDGreaterThanOrEqualTo(String value) {
            addCriterion("CreateUserID >=", value, "createUserID");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDLessThan(String value) {
            addCriterion("CreateUserID <", value, "createUserID");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDLessThanOrEqualTo(String value) {
            addCriterion("CreateUserID <=", value, "createUserID");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDLike(String value) {
            addCriterion("CreateUserID like", value, "createUserID");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDNotLike(String value) {
            addCriterion("CreateUserID not like", value, "createUserID");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDIn(List<String> values) {
            addCriterion("CreateUserID in", values, "createUserID");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDNotIn(List<String> values) {
            addCriterion("CreateUserID not in", values, "createUserID");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDBetween(String value1, String value2) {
            addCriterion("CreateUserID between", value1, value2, "createUserID");
            return (Criteria) this;
        }

        public Criteria andCreateUserIDNotBetween(String value1, String value2) {
            addCriterion("CreateUserID not between", value1, value2, "createUserID");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CreateTime is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CreateTime =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CreateTime <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CreateTime >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CreateTime >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CreateTime <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CreateTime <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CreateTime in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CreateTime not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CreateTime between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CreateTime not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDIsNull() {
            addCriterion("ModifyUserID is null");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDIsNotNull() {
            addCriterion("ModifyUserID is not null");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDEqualTo(String value) {
            addCriterion("ModifyUserID =", value, "modifyUserID");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDNotEqualTo(String value) {
            addCriterion("ModifyUserID <>", value, "modifyUserID");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDGreaterThan(String value) {
            addCriterion("ModifyUserID >", value, "modifyUserID");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDGreaterThanOrEqualTo(String value) {
            addCriterion("ModifyUserID >=", value, "modifyUserID");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDLessThan(String value) {
            addCriterion("ModifyUserID <", value, "modifyUserID");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDLessThanOrEqualTo(String value) {
            addCriterion("ModifyUserID <=", value, "modifyUserID");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDLike(String value) {
            addCriterion("ModifyUserID like", value, "modifyUserID");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDNotLike(String value) {
            addCriterion("ModifyUserID not like", value, "modifyUserID");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDIn(List<String> values) {
            addCriterion("ModifyUserID in", values, "modifyUserID");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDNotIn(List<String> values) {
            addCriterion("ModifyUserID not in", values, "modifyUserID");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDBetween(String value1, String value2) {
            addCriterion("ModifyUserID between", value1, value2, "modifyUserID");
            return (Criteria) this;
        }

        public Criteria andModifyUserIDNotBetween(String value1, String value2) {
            addCriterion("ModifyUserID not between", value1, value2, "modifyUserID");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("ModifyTime is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("ModifyTime is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("ModifyTime =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("ModifyTime <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("ModifyTime >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ModifyTime >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("ModifyTime <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("ModifyTime <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("ModifyTime in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("ModifyTime not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("ModifyTime between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("ModifyTime not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}