package com.timizatechnologies.crm.api_client

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @FormUrlEncoded
    @POST("Api/employeeregistration.php")
    fun employeeregister(
        @Field("emp_name") emp_name: String?,
        @Field("emp_email") emp_email: String?,
        @Field("emp_password") emp_password: String?,
        @Field("emp_mobile") emp_mobile: String?,
        @Field("emp_designation") emp_designation: String?,
        @Field("emp_gender") emp_gender: String?,
        @Field("emp_profile") emp_profile: String?,
        @Field("emp_address") emp_address: String?,
        @Field("emp_caddress") emp_caddress: String?,
        @Field("token") token: String?
    ): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/loginuser.php")
    fun employeelogin(
        @Field("emp_email") emp_email: String?,
        @Field("emp_pwd") emp_pwd: String?
    ): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/checkemployeemobile.php")
    fun mobileVerification(@Field("emp_mobile") emp_mobile: String?): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/resetpassword.php")
    fun resetPassword(
        @Field("emp_mobile") emp_mobile: String?,
        @Field("emp_password") emp_password: String?
    ): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/employeeleave.php")
    fun empleave(
        @Field("emp_id") emp_id: String?,
        @Field("leave_type") leave_type: String?,
        @Field("start_date") start_date: String?,
        @Field("end_date") end_date: String?,
        @Field("leave_desc") leave_desc: String?,
        @Field("days") days: String?,
        @Field("balance") balance: String?,
        @Field("applied_to") applied_to: String?,
        @Field("cc_to") cc_to: String?
    ): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/empleavedata.php")
    fun empleavedata(@Field("emp_id") emp_id: String?): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/empsalarydata.php")
    fun empSaldata(@Field("emp_id") emp_id: String?): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/taskdata.php")
    fun taskdata(@Field("emp_id") emp_id: String?): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/emptaskdata.php")
    fun emptaskdata(@Field("emp_id") emp_id: String?): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/emptask.php")
    fun emptask(
        @Field("emp_id") emp_id: String?,
        @Field("task_title") task_title: String?,
        @Field("task_deadline") task_deadline: String?,
        @Field("task_givenby") task_givenby: String?,
        @Field("task_desc") task_desc: String?
    ): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/emptaskstatus.php")
    fun emptaskstatus(
        @Field("emp_task_id") emp_task_id: String?,
        @Field("task_status") task_status: String?
    ): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/emptimesheet.php")
    fun timesheet(
        @Field("emp_id") emp_id: String?,
        @Field("admin_task_id") admin_task_id: String?,
        @Field("task_title") task_title: String?,
        @Field("start_time") start_time: String?,
        @Field("end_time") end_time: String?,
        @Field("task_desc") task_desc: String?,
        @Field("task_status") task_status: String?
    ): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/dailystatusdata.php")
    fun dailystatusdata(@Field("emp_id") emp_id: String?): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/updateprofilepic.php")
    fun updateprofilepic(
        @Field("emp_id") emp_id: String?,
        @Field("imgString") imgString: String?
    ): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/updateprofile.php")
    fun updateprofile(
        @Field("emp_id") emp_id: String?,
        @Field("emp_name") emp_name: String?,
        @Field("emp_mobile") emp_mobile: String?,
        @Field("emp_email") emp_email: String?,
        @Field("emp_designation") emp_designation: String?,
        @Field("emp_address") emp_address: String?
    ): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/employeetoken.php")
    fun employeetoken(@Field("emp_email") emp_email: String?): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/deleteuser.php")
    fun deleteuser(@Field("emp_id") emp_id: String?): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/mailsend.php")
    fun mailsend(
        @Field("from") from: String?,
        @Field("to") to: String?,
        @Field("subject") subject: String?,
        @Field("body") body: String?,
        @Field("document") document: String?
    ): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/fetchmail.php")
    fun fetchmail(@Field("emp_email") emp_email: String?): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/mailnotification.php")
    fun mailnotification(@Field("emp_email") emp_email: String?): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/balancegraph.php")
    fun balancegraph(@Field("emp_id") emp_id: String?): Call<JsonElement?>?

    @FormUrlEncoded
    @POST("Api/taskgraph.php")
    fun taskgraph(@Field("emp_id") emp_id: String?): Call<JsonElement?>?
}
