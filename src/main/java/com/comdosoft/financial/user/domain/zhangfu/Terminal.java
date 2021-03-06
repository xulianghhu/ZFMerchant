package com.comdosoft.financial.user.domain.zhangfu;

import java.util.Date;

public class Terminal {
	
	/**
     * 已开通
     */
    public static final int TerminalTYPEID_1 = 1;

    /**
     * 部分开通
     */
    public static final int TerminalTYPEID_2 = 2;

    /**
     * 未开通
     */
    public static final int TerminalTYPEID_3 = 3;
    
    /**
     * 是否回到售后库（否）
     */
    public static final int IS_RETURN_CS_DEPOTS_NO = 0;
    
    /**
     * 是否回到售后库（是）
     */
    public static final int IS_RETURN_CS_DEPOTS_YES = 1;
    
    /**
     * 生成类型（系统添加）
     */
    public static final int SYSTYPE = 0;
    
    /**
     * 生成类型（自助绑定）
     */
    public static final int SELFTYPES = 1;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.id
	 * @mbggenerated
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.good_id
	 * @mbggenerated
	 */
	private Integer goodId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.serial_num
	 * @mbggenerated
	 */
	private String serialNum;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.pay_channel_id
	 * @mbggenerated
	 */
	private Integer payChannelId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.merchant_id
	 * @mbggenerated
	 */
	private Integer merchantId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.customer_id
	 * @mbggenerated
	 */
	private Integer customerId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.status
	 * @mbggenerated
	 */
	private Byte status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.agent_id
	 * @mbggenerated
	 */
	private Integer agentId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.order_id
	 * @mbggenerated
	 */
	private Integer orderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.account
	 * @mbggenerated
	 */
	private String account;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.password
	 * @mbggenerated
	 */
	private String password;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.is_return_cs_depots
	 * @mbggenerated
	 */
	private Boolean isReturnCsDepots;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.created_at
	 * @mbggenerated
	 */
	private Date createdAt;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.updated_at
	 * @mbggenerated
	 */
	private Date updatedAt;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.type
	 * @mbggenerated
	 */
	private Byte type;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.merchant_num
	 * @mbggenerated
	 */
	private String merchantNum;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.is_rate_wrong
	 * @mbggenerated
	 */
	private Boolean isRateWrong;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.base_rate
	 * @mbggenerated
	 */
	private Integer baseRate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.terminal_marks_id
	 * @mbggenerated
	 */
	private Integer terminalMarksId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column terminals.billing_cycles_id
	 * @mbggenerated
	 */
	private Integer billingCyclesId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.id
	 * @return  the value of terminals.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.id
	 * @param id  the value for terminals.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.good_id
	 * @return  the value of terminals.good_id
	 * @mbggenerated
	 */
	public Integer getGoodId() {
		return goodId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.good_id
	 * @param goodId  the value for terminals.good_id
	 * @mbggenerated
	 */
	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.serial_num
	 * @return  the value of terminals.serial_num
	 * @mbggenerated
	 */
	public String getSerialNum() {
		return serialNum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.serial_num
	 * @param serialNum  the value for terminals.serial_num
	 * @mbggenerated
	 */
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.pay_channel_id
	 * @return  the value of terminals.pay_channel_id
	 * @mbggenerated
	 */
	public Integer getPayChannelId() {
		return payChannelId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.pay_channel_id
	 * @param payChannelId  the value for terminals.pay_channel_id
	 * @mbggenerated
	 */
	public void setPayChannelId(Integer payChannelId) {
		this.payChannelId = payChannelId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.merchant_id
	 * @return  the value of terminals.merchant_id
	 * @mbggenerated
	 */
	public Integer getMerchantId() {
		return merchantId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.merchant_id
	 * @param merchantId  the value for terminals.merchant_id
	 * @mbggenerated
	 */
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.customer_id
	 * @return  the value of terminals.customer_id
	 * @mbggenerated
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.customer_id
	 * @param customerId  the value for terminals.customer_id
	 * @mbggenerated
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.status
	 * @return  the value of terminals.status
	 * @mbggenerated
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.status
	 * @param status  the value for terminals.status
	 * @mbggenerated
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.agent_id
	 * @return  the value of terminals.agent_id
	 * @mbggenerated
	 */
	public Integer getAgentId() {
		return agentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.agent_id
	 * @param agentId  the value for terminals.agent_id
	 * @mbggenerated
	 */
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.order_id
	 * @return  the value of terminals.order_id
	 * @mbggenerated
	 */
	public Integer getOrderId() {
		return orderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.order_id
	 * @param orderId  the value for terminals.order_id
	 * @mbggenerated
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.account
	 * @return  the value of terminals.account
	 * @mbggenerated
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.account
	 * @param account  the value for terminals.account
	 * @mbggenerated
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.password
	 * @return  the value of terminals.password
	 * @mbggenerated
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.password
	 * @param password  the value for terminals.password
	 * @mbggenerated
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.is_return_cs_depots
	 * @return  the value of terminals.is_return_cs_depots
	 * @mbggenerated
	 */
	public Boolean getIsReturnCsDepots() {
		return isReturnCsDepots;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.is_return_cs_depots
	 * @param isReturnCsDepots  the value for terminals.is_return_cs_depots
	 * @mbggenerated
	 */
	public void setIsReturnCsDepots(Boolean isReturnCsDepots) {
		this.isReturnCsDepots = isReturnCsDepots;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.created_at
	 * @return  the value of terminals.created_at
	 * @mbggenerated
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.created_at
	 * @param createdAt  the value for terminals.created_at
	 * @mbggenerated
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.updated_at
	 * @return  the value of terminals.updated_at
	 * @mbggenerated
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.updated_at
	 * @param updatedAt  the value for terminals.updated_at
	 * @mbggenerated
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.type
	 * @return  the value of terminals.type
	 * @mbggenerated
	 */
	public Byte getType() {
		return type;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.type
	 * @param type  the value for terminals.type
	 * @mbggenerated
	 */
	public void setType(Byte type) {
		this.type = type;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.merchant_num
	 * @return  the value of terminals.merchant_num
	 * @mbggenerated
	 */
	public String getMerchantNum() {
		return merchantNum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.merchant_num
	 * @param merchantNum  the value for terminals.merchant_num
	 * @mbggenerated
	 */
	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.is_rate_wrong
	 * @return  the value of terminals.is_rate_wrong
	 * @mbggenerated
	 */
	public Boolean getIsRateWrong() {
		return isRateWrong;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.is_rate_wrong
	 * @param isRateWrong  the value for terminals.is_rate_wrong
	 * @mbggenerated
	 */
	public void setIsRateWrong(Boolean isRateWrong) {
		this.isRateWrong = isRateWrong;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.base_rate
	 * @return  the value of terminals.base_rate
	 * @mbggenerated
	 */
	public Integer getBaseRate() {
		return baseRate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.base_rate
	 * @param baseRate  the value for terminals.base_rate
	 * @mbggenerated
	 */
	public void setBaseRate(Integer baseRate) {
		this.baseRate = baseRate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.terminal_marks_id
	 * @return  the value of terminals.terminal_marks_id
	 * @mbggenerated
	 */
	public Integer getTerminalMarksId() {
		return terminalMarksId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.terminal_marks_id
	 * @param terminalMarksId  the value for terminals.terminal_marks_id
	 * @mbggenerated
	 */
	public void setTerminalMarksId(Integer terminalMarksId) {
		this.terminalMarksId = terminalMarksId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column terminals.billing_cycles_id
	 * @return  the value of terminals.billing_cycles_id
	 * @mbggenerated
	 */
	public Integer getBillingCyclesId() {
		return billingCyclesId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column terminals.billing_cycles_id
	 * @param billingCyclesId  the value for terminals.billing_cycles_id
	 * @mbggenerated
	 */
	public void setBillingCyclesId(Integer billingCyclesId) {
		this.billingCyclesId = billingCyclesId;
	}
}