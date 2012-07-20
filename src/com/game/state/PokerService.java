/*
 * @ Project: Pokerclient
 * @ File Name: PokerService.java
 * @ Date: 2012-7-20
 * @ Author: Song,Jie
 * Copyright 2012 NHNST. All Rights Reserved.
 */
package com.game.state;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.game.model.fightlandlord.FightLandlordGame;
import com.game.model.fightlandlord.FightLandlordPoker;

/**
 * Class Description
 * 
 * <pre>
 * Filename        : PokerService.java , 2012-7-20
 * Creator         : SongJie
 * Revised Description
 * ----------------------------------------------
 * revised date    reviser   revised contents
 * ----------------------------------------------
 * 
 * </pre>
 */
public class PokerService extends Service {
	private static final String TAG = "PokerService";

	private PokerBinder mBinder = new PokerBinder();

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "start IBinder~~~");
		return mBinder;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");

	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Log.d(TAG, "onStart");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.e(TAG, "start onUnbind~~~");
		return super.onUnbind(intent);
	}

	public String getFightLandlordPoker() {
		// 开始洗牌与发牌，排序功能与出牌规则在客户端完成
        boolean isFirstOut = false;
        FightLandlordPoker[][] eachShuffledPokers = FightLandlordPoker.shuffle();
        // 取得合作玩家手中所持有的牌数
        String pokerNumberOfEachPlayer = "";
        for (int index = 0; index < eachShuffledPokers.length; index++) {
            int lastIndex = eachShuffledPokers[index].length - 1;
            pokerNumberOfEachPlayer += index + "=";
            if (eachShuffledPokers[index][lastIndex] == null) {
                pokerNumberOfEachPlayer += (eachShuffledPokers[index].length - 1) + ","; 
            } else {
                pokerNumberOfEachPlayer += eachShuffledPokers[index].length + ",";
            }
        }
        pokerNumberOfEachPlayer = pokerNumberOfEachPlayer.replaceFirst(",$", "");
        // 准备发牌开始游戏
        String firstPlayerNumber = null;
        StringBuilder builderTemp = new StringBuilder();
        String playerCards[] = new String[eachShuffledPokers.length];
        for (int m = 0; m < eachShuffledPokers.length; m++) {
            StringBuilder builder = new StringBuilder();
            for (int n = 0; n < eachShuffledPokers[m].length; n++) {
            	builderTemp.append(eachShuffledPokers[m][n].getValue() + ",");
            	builder.append(eachShuffledPokers[m][n].getValue() + ",");
            }
            playerCards[m] = builder.toString();
        }
        // 由于斗地主在发牌前会扣掉三张底牌，所以可能会将首发牌标志牌(红桃3)扣掉。
        // 确定开始首次发牌标识
        // 判断当前17 * 3 的扑克中是否包括发牌标识
        String currentAllCard = builderTemp.toString();
        boolean boolPK = false;
        // 是否包括红桃3标识牌
        String startPorker = FightLandlordGame.START_POKER.getValue();
        if (currentAllCard.matches("^.*" + startPorker  + ".*$")) {
        	boolPK = true;
        }
        // 是否包括方块3标识牌
        if (!boolPK && currentAllCard.matches("^.*" + FightLandlordGame.START_POKER_DIAMOND.getValue()  + ".*$")) {
        	startPorker = FightLandlordGame.START_POKER_DIAMOND.getValue();
            boolPK = true;
        }
     	// 是否包括黑桃3标识牌
        if (!boolPK && currentAllCard.matches("^.*" + FightLandlordGame.START_POKER_SPADE.getValue()  + ".*$")) {
        	startPorker = FightLandlordGame.START_POKER_SPADE.getValue();
        	boolPK = true;
        }
        // 不包括前三种花色则直接设定梅花3为首发牌标识
        if (!boolPK) {
        	startPorker = FightLandlordGame.START_POKER_CLUB.getValue();
        }
		return null;
	}

	public class PokerBinder extends Binder {
		PokerService getService() {
			return PokerService.this;
		}
	}
}
