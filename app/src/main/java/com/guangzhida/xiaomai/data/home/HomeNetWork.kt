package com.guangzhida.xiaomai.data.home

import com.guangzhida.xiaomai.base.BaseResult
import com.guangzhida.xiaomai.http.RetrofitManager
import com.guangzhida.xiaomai.model.*
import com.guangzhida.xiaomai.utils.LogUtils
import okhttp3.ResponseBody
import retrofit2.Call
import java.util.concurrent.Callable

class HomeNetWork {

    private val mService by lazy { RetrofitManager.getInstance().create(HomeService::class.java) }

    suspend fun getAccountInfo(phone: String): AccountModel {
        return mService.getAccountInfo(phone)
    }

    suspend fun getPackageInfo(phone: String): ResponseBody {
        return mService.getPackageInfo(phone)
    }

    suspend fun getSchoolModelInfo(): SchoolModelWrap {
        return mService.getSchoolInfo()
    }

    /**
     * 获取学校信息通过学校地址
     */
    suspend fun getSchoolInfoByName(schoolName: String): SchoolInfoModel {
        return mService.getSchoolInfoByName(schoolName)
    }

    /**
     * 一键认证
     */

    suspend fun doAccountVerify(url: String, params: Map<String, String?>): VerifyModel {
        return mService.doAccountVerify(url, params)
    }

    suspend fun doAccountVerify2(url: String, params: Map<String, String?>): ResponseBody {
        return mService.doAccountVerify2(url, params)
    }

    /**
     * 退出认证
     */
    suspend fun exitAccountVerify(url: String, params: Map<String, String?>): VerifyModel {
        return mService.exitAccountVerify(url, params)
    }

    /**
     * 修改凌风密码
     */
    suspend fun modifyAccountPassword(url: String, params: Map<String, String?>): ResponseBody {
        return mService.modifyAccountPassword(url, params)
    }

    /**
     * 清空账号套餐信息
     */
    suspend fun clearAccountPackage(url: String, params: Map<String, String?>): ResponseBody {
        return mService.clearAccountPackage(url, params)
    }

    /**
     * 绑定校园卡套餐
     */
    suspend fun bindSchoolAccount(url: String, params: Map<String, String?>): ResponseBody {
        return mService.bindSchoolAccount(url, params)
    }

    suspend fun doLoginBySchoolAccount(schoolAccount: String, schoolPassword: String): UserModel {
        return mService.doLoginBySchoolAccount(schoolAccount, schoolPassword)
    }

    suspend fun doBindSchoolAccount(
        schoolAccount: String,
        schoolPassword: String,
        id: String
    ): UserModel {
        return mService.doBindSchoolAccount(schoolAccount, schoolPassword, id)
    }

    /**
     * 获取客服通过学校ID
     */
    suspend fun getServiceBySchoolId(schoolId: String): BaseResult<List<ServiceModel>> {
        return mService.getServiceBySchoolId(schoolId)
    }

    /**
     * 通过学校ID获取弹窗广告
     */
    suspend fun getPopAdBySchoolId(schoolId: String): BaseResult<PopAdModel> {
        return mService.getPopAdBySchoolId(schoolId)
    }

    companion object {
        @Volatile
        private var netWork: HomeNetWork? = null

        fun getInstance() = netWork
            ?: synchronized(this) {
                netWork
                    ?: HomeNetWork().also { netWork = it }
            }
    }


}