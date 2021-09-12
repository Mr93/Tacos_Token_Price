package com.mamram.checktezostokenprice.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class StorageModel(
    @SerializedName("veto")
    var veto: String = "",
    @SerializedName("vetos")
    var vetos: Int = 0,
    @SerializedName("votes")
    var votes: Int = 0,
    @SerializedName("ledger")
    var ledger: Int = 0,
    @SerializedName("reward")
    var reward: String = "",
    @SerializedName("voters")
    var voters: Int = 0,
    @SerializedName("tez_pool")
    var tezPool: String = "",
    @SerializedName("token_id")
    var tokenId: String = "",
    @SerializedName("last_veto")
    var lastVeto: String = "",
    @SerializedName("token_pool")
    var tokenPool: String = "",
    @SerializedName("reward_paid")
    var rewardPaid: String = "",
    @SerializedName("total_votes")
    var totalVotes: String = "",
    @SerializedName("total_reward")
    var totalReward: String = "",
    @SerializedName("total_supply")
    var totalSupply: String = "",
    @SerializedName("user_rewards")
    var userRewards: Int = 0,
    @SerializedName("period_finish")
    var periodFinish: String = "",
    @SerializedName("token_address")
    var tokenAddress: String = "",
    @SerializedName("reward_per_sec")
    var rewardPerSec: String = "",
    @SerializedName("baker_validator")
    var bakerValidator: String = "",
    @SerializedName("last_update_time")
    var lastUpdateTime: String = "",
    @SerializedName("reward_per_share")
    var rewardPerShare: String = "",
    @SerializedName("current_candidate")
    var currentCandidate: String = "",
    @SerializedName("current_delegated")
    var currentDelegated: String = ""
) : Parcelable