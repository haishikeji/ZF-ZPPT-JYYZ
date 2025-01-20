package com.px.employmentsite.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PcInstitutionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcInstitutionExample() {
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

        public Criteria andInstitutionIDIsNull() {
            addCriterion("InstitutionID is null");
            return (Criteria) this;
        }

        public Criteria andInstitutionIDIsNotNull() {
            addCriterion("InstitutionID is not null");
            return (Criteria) this;
        }

        public Criteria andInstitutionIDEqualTo(String value) {
            addCriterion("InstitutionID =", value, "institutionID");
            return (Criteria) this;
        }

        public Criteria andInstitutionIDNotEqualTo(String value) {
            addCriterion("InstitutionID <>", value, "institutionID");
            return (Criteria) this;
        }

        public Criteria andInstitutionIDGreaterThan(String value) {
            addCriterion("InstitutionID >", value, "institutionID");
            return (Criteria) this;
        }

        public Criteria andInstitutionIDGreaterThanOrEqualTo(String value) {
            addCriterion("InstitutionID >=", value, "institutionID");
            return (Criteria) this;
        }

        public Criteria andInstitutionIDLessThan(String value) {
            addCriterion("InstitutionID <", value, "institutionID");
            return (Criteria) this;
        }

        public Criteria andInstitutionIDLessThanOrEqualTo(String value) {
            addCriterion("InstitutionID <=", value, "institutionID");
            return (Criteria) this;
        }

        public Criteria andInstitutionIDLike(String value) {
            addCriterion("InstitutionID like", value, "institutionID");
            return (Criteria) this;
        }

        public Criteria andInstitutionIDNotLike(String value) {
            addCriterion("InstitutionID not like", value, "institutionID");
            return (Criteria) this;
        }

        public Criteria andInstitutionIDIn(List<String> values) {
            addCriterion("InstitutionID in", values, "institutionID");
            return (Criteria) this;
        }

        public Criteria andInstitutionIDNotIn(List<String> values) {
            addCriterion("InstitutionID not in", values, "institutionID");
            return (Criteria) this;
        }

        public Criteria andInstitutionIDBetween(String value1, String value2) {
            addCriterion("InstitutionID between", value1, value2, "institutionID");
            return (Criteria) this;
        }

        public Criteria andInstitutionIDNotBetween(String value1, String value2) {
            addCriterion("InstitutionID not between", value1, value2, "institutionID");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNull() {
            addCriterion("CompanyName is null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNotNull() {
            addCriterion("CompanyName is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameEqualTo(String value) {
            addCriterion("CompanyName =", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotEqualTo(String value) {
            addCriterion("CompanyName <>", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThan(String value) {
            addCriterion("CompanyName >", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThanOrEqualTo(String value) {
            addCriterion("CompanyName >=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThan(String value) {
            addCriterion("CompanyName <", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThanOrEqualTo(String value) {
            addCriterion("CompanyName <=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLike(String value) {
            addCriterion("CompanyName like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotLike(String value) {
            addCriterion("CompanyName not like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIn(List<String> values) {
            addCriterion("CompanyName in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotIn(List<String> values) {
            addCriterion("CompanyName not in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameBetween(String value1, String value2) {
            addCriterion("CompanyName between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotBetween(String value1, String value2) {
            addCriterion("CompanyName not between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressIsNull() {
            addCriterion("CompanyAddress is null");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressIsNotNull() {
            addCriterion("CompanyAddress is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressEqualTo(String value) {
            addCriterion("CompanyAddress =", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressNotEqualTo(String value) {
            addCriterion("CompanyAddress <>", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressGreaterThan(String value) {
            addCriterion("CompanyAddress >", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressGreaterThanOrEqualTo(String value) {
            addCriterion("CompanyAddress >=", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressLessThan(String value) {
            addCriterion("CompanyAddress <", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressLessThanOrEqualTo(String value) {
            addCriterion("CompanyAddress <=", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressLike(String value) {
            addCriterion("CompanyAddress like", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressNotLike(String value) {
            addCriterion("CompanyAddress not like", value, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressIn(List<String> values) {
            addCriterion("CompanyAddress in", values, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressNotIn(List<String> values) {
            addCriterion("CompanyAddress not in", values, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressBetween(String value1, String value2) {
            addCriterion("CompanyAddress between", value1, value2, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andCompanyAddressNotBetween(String value1, String value2) {
            addCriterion("CompanyAddress not between", value1, value2, "companyAddress");
            return (Criteria) this;
        }

        public Criteria andFzrNameIsNull() {
            addCriterion("FzrName is null");
            return (Criteria) this;
        }

        public Criteria andFzrNameIsNotNull() {
            addCriterion("FzrName is not null");
            return (Criteria) this;
        }

        public Criteria andFzrNameEqualTo(String value) {
            addCriterion("FzrName =", value, "fzrName");
            return (Criteria) this;
        }

        public Criteria andFzrNameNotEqualTo(String value) {
            addCriterion("FzrName <>", value, "fzrName");
            return (Criteria) this;
        }

        public Criteria andFzrNameGreaterThan(String value) {
            addCriterion("FzrName >", value, "fzrName");
            return (Criteria) this;
        }

        public Criteria andFzrNameGreaterThanOrEqualTo(String value) {
            addCriterion("FzrName >=", value, "fzrName");
            return (Criteria) this;
        }

        public Criteria andFzrNameLessThan(String value) {
            addCriterion("FzrName <", value, "fzrName");
            return (Criteria) this;
        }

        public Criteria andFzrNameLessThanOrEqualTo(String value) {
            addCriterion("FzrName <=", value, "fzrName");
            return (Criteria) this;
        }

        public Criteria andFzrNameLike(String value) {
            addCriterion("FzrName like", value, "fzrName");
            return (Criteria) this;
        }

        public Criteria andFzrNameNotLike(String value) {
            addCriterion("FzrName not like", value, "fzrName");
            return (Criteria) this;
        }

        public Criteria andFzrNameIn(List<String> values) {
            addCriterion("FzrName in", values, "fzrName");
            return (Criteria) this;
        }

        public Criteria andFzrNameNotIn(List<String> values) {
            addCriterion("FzrName not in", values, "fzrName");
            return (Criteria) this;
        }

        public Criteria andFzrNameBetween(String value1, String value2) {
            addCriterion("FzrName between", value1, value2, "fzrName");
            return (Criteria) this;
        }

        public Criteria andFzrNameNotBetween(String value1, String value2) {
            addCriterion("FzrName not between", value1, value2, "fzrName");
            return (Criteria) this;
        }

        public Criteria andFzrMobileIsNull() {
            addCriterion("FzrMobile is null");
            return (Criteria) this;
        }

        public Criteria andFzrMobileIsNotNull() {
            addCriterion("FzrMobile is not null");
            return (Criteria) this;
        }

        public Criteria andFzrMobileEqualTo(String value) {
            addCriterion("FzrMobile =", value, "fzrMobile");
            return (Criteria) this;
        }

        public Criteria andFzrMobileNotEqualTo(String value) {
            addCriterion("FzrMobile <>", value, "fzrMobile");
            return (Criteria) this;
        }

        public Criteria andFzrMobileGreaterThan(String value) {
            addCriterion("FzrMobile >", value, "fzrMobile");
            return (Criteria) this;
        }

        public Criteria andFzrMobileGreaterThanOrEqualTo(String value) {
            addCriterion("FzrMobile >=", value, "fzrMobile");
            return (Criteria) this;
        }

        public Criteria andFzrMobileLessThan(String value) {
            addCriterion("FzrMobile <", value, "fzrMobile");
            return (Criteria) this;
        }

        public Criteria andFzrMobileLessThanOrEqualTo(String value) {
            addCriterion("FzrMobile <=", value, "fzrMobile");
            return (Criteria) this;
        }

        public Criteria andFzrMobileLike(String value) {
            addCriterion("FzrMobile like", value, "fzrMobile");
            return (Criteria) this;
        }

        public Criteria andFzrMobileNotLike(String value) {
            addCriterion("FzrMobile not like", value, "fzrMobile");
            return (Criteria) this;
        }

        public Criteria andFzrMobileIn(List<String> values) {
            addCriterion("FzrMobile in", values, "fzrMobile");
            return (Criteria) this;
        }

        public Criteria andFzrMobileNotIn(List<String> values) {
            addCriterion("FzrMobile not in", values, "fzrMobile");
            return (Criteria) this;
        }

        public Criteria andFzrMobileBetween(String value1, String value2) {
            addCriterion("FzrMobile between", value1, value2, "fzrMobile");
            return (Criteria) this;
        }

        public Criteria andFzrMobileNotBetween(String value1, String value2) {
            addCriterion("FzrMobile not between", value1, value2, "fzrMobile");
            return (Criteria) this;
        }

        public Criteria andFrNameIsNull() {
            addCriterion("FrName is null");
            return (Criteria) this;
        }

        public Criteria andFrNameIsNotNull() {
            addCriterion("FrName is not null");
            return (Criteria) this;
        }

        public Criteria andFrNameEqualTo(String value) {
            addCriterion("FrName =", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameNotEqualTo(String value) {
            addCriterion("FrName <>", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameGreaterThan(String value) {
            addCriterion("FrName >", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameGreaterThanOrEqualTo(String value) {
            addCriterion("FrName >=", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameLessThan(String value) {
            addCriterion("FrName <", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameLessThanOrEqualTo(String value) {
            addCriterion("FrName <=", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameLike(String value) {
            addCriterion("FrName like", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameNotLike(String value) {
            addCriterion("FrName not like", value, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameIn(List<String> values) {
            addCriterion("FrName in", values, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameNotIn(List<String> values) {
            addCriterion("FrName not in", values, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameBetween(String value1, String value2) {
            addCriterion("FrName between", value1, value2, "frName");
            return (Criteria) this;
        }

        public Criteria andFrNameNotBetween(String value1, String value2) {
            addCriterion("FrName not between", value1, value2, "frName");
            return (Criteria) this;
        }

        public Criteria andFrMobileIsNull() {
            addCriterion("FrMobile is null");
            return (Criteria) this;
        }

        public Criteria andFrMobileIsNotNull() {
            addCriterion("FrMobile is not null");
            return (Criteria) this;
        }

        public Criteria andFrMobileEqualTo(String value) {
            addCriterion("FrMobile =", value, "frMobile");
            return (Criteria) this;
        }

        public Criteria andFrMobileNotEqualTo(String value) {
            addCriterion("FrMobile <>", value, "frMobile");
            return (Criteria) this;
        }

        public Criteria andFrMobileGreaterThan(String value) {
            addCriterion("FrMobile >", value, "frMobile");
            return (Criteria) this;
        }

        public Criteria andFrMobileGreaterThanOrEqualTo(String value) {
            addCriterion("FrMobile >=", value, "frMobile");
            return (Criteria) this;
        }

        public Criteria andFrMobileLessThan(String value) {
            addCriterion("FrMobile <", value, "frMobile");
            return (Criteria) this;
        }

        public Criteria andFrMobileLessThanOrEqualTo(String value) {
            addCriterion("FrMobile <=", value, "frMobile");
            return (Criteria) this;
        }

        public Criteria andFrMobileLike(String value) {
            addCriterion("FrMobile like", value, "frMobile");
            return (Criteria) this;
        }

        public Criteria andFrMobileNotLike(String value) {
            addCriterion("FrMobile not like", value, "frMobile");
            return (Criteria) this;
        }

        public Criteria andFrMobileIn(List<String> values) {
            addCriterion("FrMobile in", values, "frMobile");
            return (Criteria) this;
        }

        public Criteria andFrMobileNotIn(List<String> values) {
            addCriterion("FrMobile not in", values, "frMobile");
            return (Criteria) this;
        }

        public Criteria andFrMobileBetween(String value1, String value2) {
            addCriterion("FrMobile between", value1, value2, "frMobile");
            return (Criteria) this;
        }

        public Criteria andFrMobileNotBetween(String value1, String value2) {
            addCriterion("FrMobile not between", value1, value2, "frMobile");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeIsNull() {
            addCriterion("CompanyCode is null");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeIsNotNull() {
            addCriterion("CompanyCode is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeEqualTo(String value) {
            addCriterion("CompanyCode =", value, "companyCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeNotEqualTo(String value) {
            addCriterion("CompanyCode <>", value, "companyCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeGreaterThan(String value) {
            addCriterion("CompanyCode >", value, "companyCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("CompanyCode >=", value, "companyCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeLessThan(String value) {
            addCriterion("CompanyCode <", value, "companyCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeLessThanOrEqualTo(String value) {
            addCriterion("CompanyCode <=", value, "companyCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeLike(String value) {
            addCriterion("CompanyCode like", value, "companyCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeNotLike(String value) {
            addCriterion("CompanyCode not like", value, "companyCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeIn(List<String> values) {
            addCriterion("CompanyCode in", values, "companyCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeNotIn(List<String> values) {
            addCriterion("CompanyCode not in", values, "companyCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeBetween(String value1, String value2) {
            addCriterion("CompanyCode between", value1, value2, "companyCode");
            return (Criteria) this;
        }

        public Criteria andCompanyCodeNotBetween(String value1, String value2) {
            addCriterion("CompanyCode not between", value1, value2, "companyCode");
            return (Criteria) this;
        }

        public Criteria andCompanyDescIsNull() {
            addCriterion("CompanyDesc is null");
            return (Criteria) this;
        }

        public Criteria andCompanyDescIsNotNull() {
            addCriterion("CompanyDesc is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyDescEqualTo(String value) {
            addCriterion("CompanyDesc =", value, "companyDesc");
            return (Criteria) this;
        }

        public Criteria andCompanyDescNotEqualTo(String value) {
            addCriterion("CompanyDesc <>", value, "companyDesc");
            return (Criteria) this;
        }

        public Criteria andCompanyDescGreaterThan(String value) {
            addCriterion("CompanyDesc >", value, "companyDesc");
            return (Criteria) this;
        }

        public Criteria andCompanyDescGreaterThanOrEqualTo(String value) {
            addCriterion("CompanyDesc >=", value, "companyDesc");
            return (Criteria) this;
        }

        public Criteria andCompanyDescLessThan(String value) {
            addCriterion("CompanyDesc <", value, "companyDesc");
            return (Criteria) this;
        }

        public Criteria andCompanyDescLessThanOrEqualTo(String value) {
            addCriterion("CompanyDesc <=", value, "companyDesc");
            return (Criteria) this;
        }

        public Criteria andCompanyDescLike(String value) {
            addCriterion("CompanyDesc like", value, "companyDesc");
            return (Criteria) this;
        }

        public Criteria andCompanyDescNotLike(String value) {
            addCriterion("CompanyDesc not like", value, "companyDesc");
            return (Criteria) this;
        }

        public Criteria andCompanyDescIn(List<String> values) {
            addCriterion("CompanyDesc in", values, "companyDesc");
            return (Criteria) this;
        }

        public Criteria andCompanyDescNotIn(List<String> values) {
            addCriterion("CompanyDesc not in", values, "companyDesc");
            return (Criteria) this;
        }

        public Criteria andCompanyDescBetween(String value1, String value2) {
            addCriterion("CompanyDesc between", value1, value2, "companyDesc");
            return (Criteria) this;
        }

        public Criteria andCompanyDescNotBetween(String value1, String value2) {
            addCriterion("CompanyDesc not between", value1, value2, "companyDesc");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("Remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("Remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("Remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("Remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("Remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("Remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("Remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("Remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("Remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("Remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("Remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("Remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("Remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("Remark not between", value1, value2, "remark");
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