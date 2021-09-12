package com.mamram.checktezostokenprice.data.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mamram.checktezostokenprice.data.model.StorageModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class StorageDto(
    @SerializedName("veto")
    val veto: String?,
    @SerializedName("vetos")
    val vetos: Int?,
    @SerializedName("votes")
    val votes: Int?,
    @SerializedName("ledger")
    val ledger: Int?,
    @SerializedName("reward")
    val reward: String?,
    @SerializedName("voters")
    val voters: Int?,
    @SerializedName("tez_pool")
    val tezPool: String?,
    @SerializedName("token_id")
    val tokenId: String?,
    @SerializedName("last_veto")
    val lastVeto: String?,
    @SerializedName("token_pool")
    val tokenPool: String?,
    @SerializedName("reward_paid")
    val rewardPaid: String?,
    @SerializedName("total_votes")
    val totalVotes: String?,
    @SerializedName("total_reward")
    val totalReward: String?,
    @SerializedName("total_supply")
    val totalSupply: String?,
    @SerializedName("user_rewards")
    val userRewards: Int?,
    @SerializedName("period_finish")
    val periodFinish: String?,
    @SerializedName("token_address")
    val tokenAddress: String?,
    @SerializedName("reward_per_sec")
    val rewardPerSec: String?,
    @SerializedName("baker_validator")
    val bakerValidator: String?,
    @SerializedName("last_update_time")
    val lastUpdateTime: String?,
    @SerializedName("reward_per_share")
    val rewardPerShare: String?,
    @SerializedName("current_candidate")
    val currentCandidate: String?,
    @SerializedName("current_delegated")
    val currentDelegated: String?
) : Parcelable

fun StorageDto.toModel(): StorageModel {
    return StorageModel(tezPool = tezPool ?: "0", tokenPool = tokenPool ?: "0l")
}