package com.fecmobile.jiubeirobot.ui.fragment.shop;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseApplication;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.bean.IdentityCheckResultBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.bean.ShopDetailsBean;
import com.fecmobile.jiubeirobot.bean.StockBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.common.MyWebView;
import com.fecmobile.jiubeirobot.common.view.BannerLayout;
import com.fecmobile.jiubeirobot.common.view.CustomNumInputView;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.ui.activity.shop.ShopListActivity;
import com.fecmobile.jiubeirobot.ui.activity.shop.UserCheckOrderActivity;
import com.fecmobile.jiubeirobot.ui.dialog.user.IdentityCheckDialog;
import com.fecmobile.jiubeirobot.utils.Arith;
import com.fecmobile.jiubeirobot.utils.BasicTool;
import com.fecmobile.jiubeirobot.utils.HtmlRegexpUtil;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.T;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :商城商品详情
 * 包名      : com.fecmobile.jiubeirobot.ui.fragment.shop
 * 类名称    : ShopDetailsFragment
 * 创建人    : ghy
 * 创建时间  : 2017/2/23 16:19
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ShopDetailsFragment extends BaseFragment {
    @BindView(R.id.bl_shop_banner)
    BannerLayout blShopBanner;
    @BindView(R.id.iv_close)
    ImageView ivClose;
//    @BindView(R.id.tv_wine_type)
//    TextView tvWineType;//品类
//    @BindView(R.id.tv_bilingual)
//    TextView tvBilingual;//品牌中英文
//    @BindView(R.id.tv_details_level)
//    TextView tvDetailsLevel;//酒庄级别
//    @BindView(R.id.tv_taste)
//    TextView tvTaste;//口感分类
//    @BindView(R.id.tv_from_ingesting)
//    TextView tvFromIngesting;//建议醒酒时间
//    @BindView(R.id.tv_from_temperature)
//    TextView tvFromTemperature;//适宜温度
//    @BindView(R.id.tv_from_suggest)
//    TextView tvFromSuggest;//配餐建议


    //规格参数 所有绑定
    @BindView(R.id.tv_wine_type)
    TextView tvWineType;//类型
    @BindView(R.id.tv_region)
    TextView tv_region;//产区
    @BindView(R.id.tv_chateau)
    TextView tv_chateau;//酒庄
    @BindView(R.id.tv_leavel)
    TextView tv_leavel;//等级
    @BindView(R.id.tv_year)
    TextView tv_year;//年份
    @BindView(R.id.tv_alcoholic_strength)
    TextView tv_alcoholic_strength;//酒精度
    @BindView(R.id.tv_grape_variety)
    TextView tv_grape_variety;//葡萄品种
    @BindView(R.id.tv_colour_lustre)
    TextView tv_colour_lustre;//色泽
    @BindView(R.id.tv_fragrance)
    TextView tv_fragrance;//香气

    @BindView(R.id.tv_wine_Body)
    TextView tv_wine_Body;//酒体
    @BindView(R.id.tv_taste)
    TextView tv_taste;//口感
    @BindView(R.id.tv_Capacity_specification)
    TextView tv_Capacity_specification;//容量规格
    @BindView(R.id.tv_Storage_conditions)
    TextView tv_Storage_conditions;//存储条件
    @BindView(R.id.tv_Serving_Temp)
    TextView tv_Serving_Temp;//最佳饮用温度
    @BindView(R.id.tv_Ingesting_time)
    TextView tv_Ingesting_time;//醒酒时间
    @BindView(R.id.tv_suggestion)
    TextView tv_suggestion;//配餐建议
    @BindView(R.id.tv_award)
    TextView tv_award;//奖项
    @BindView(R.id.tv_score)
    TextView tv_score;//评分


    @BindView(R.id.mwv_graphic_details)
    MyWebView mwvGraphicDetails;//图文详情
    @BindView(R.id.mwv_chateau_histoy)
    MyWebView mwvChateauHistoy;//酒庄历史
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_chateau_name_en)
    TextView tvChateauNameEn;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_chateau_name)
    TextView tvChateauName;
    @BindView(R.id.tv_ls_stock)
    TextView tvLsStock;
    @BindView(R.id.tv_shop)
    TextView tvShop;
    @BindView(R.id.v_shop_line)
    View vShopLine;
    @BindView(R.id.rlyt_shop_tab)
    RelativeLayout rlytShopTab;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.v_details_line)
    View vDetailsLine;
    @BindView(R.id.rlyt_details_tab)
    RelativeLayout rlytDetailsTab;
    @BindView(R.id.tv_chateau_tab)
    TextView tvChateauTab;
    @BindView(R.id.v_chateau)
    View vChateau;
    @BindView(R.id.rlyt_history_tab)
    RelativeLayout rlytHistoryTab;
    @BindView(R.id.tv_shop_number)
    TextView tvShopNumber;
    @BindView(R.id.rl_card)
    RelativeLayout rlCard;
    @BindView(R.id.sv_details)
    ScrollView svDetails;
    @BindView(R.id.llyt_details_img_txt)
    LinearLayout llytDetailsImgTxt;
    @BindView(R.id.llyt_chateau_histoy)
    LinearLayout llytChateauHistoy;
    @BindView(R.id.btn_add_to_card)
    Button btnAddToCard;
    @BindView(R.id.cniv_num)
    CustomNumInputView cnivNum;
    @BindView(R.id.btn_buy)
    Button btnBuy;
//    @BindView(R.id.tv_alcoholic_strength)//酒精度
//    TextView tv_alcoholic_strength;


    private String id;
    private ShopDetailsBean sDetailsBean;

    @BindView(R.id.iv_jiujiao_history)
    ImageView ivhistory;//酒庄历史
    @BindView(R.id.iv_text_pic)
    ImageView textPic;//图文
//    @BindView(R.id.iv_guige)
//    ImageView ivguige;//规格参数

    //语音播放内容
    String ProductDescriptionH5;
    String ChateauHistory;

    String categay, Bilingual, DetailsLevel, Taste, FromIngesting, FromTemperature, FromSuggest;
    SpeechSynthesizer mTts;

    /**
     * 是否创建
     */
    protected boolean isCreate = false;

    public void setId(String id) {
        this.id = id;
    }

    private IdentityCheckDialog identityCheckDialog;

    @Override
    public int pageLayout() {
        id = getArguments().getString(Constants.INTENT_SHOP_ID);
        return R.layout.fragment_shop_details;
    }

    @Override
    public void initTitle() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }


    @Override
    public void initView() {
        blShopBanner.setFragment(this);
        isCreate = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("print", "fragment的onResume: ");
        if (id != null && id.length() > 0) {
            changeTab(0);
            //TODO  这里获取本地购买页面的详情
            APIManager.getInstance().shopDetails(getContext(), this, id);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("print", "fragment的onPause: ");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {

        }

    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_SHOP_DETAILS:

                //添加语音播放可以在这里添加  判断获取的详情是否为空  要是为空就不播放  不为空就播放当前商品的详细信息
                BaseBean<ObjBean<ShopDetailsBean>> detailsBean = bean;
                sDetailsBean = detailsBean.getData().getObj();
                List<String> urls1 = new ArrayList<>();
                if (BasicTool.isNotEmpty(sDetailsBean.getMainPic_url()))
                    urls1.add(sDetailsBean.getMainPic_url());

                if (BasicTool.isNotEmpty(sDetailsBean.getListPic1_url()))
                    urls1.add(sDetailsBean.getListPic1_url());

                if (BasicTool.isNotEmpty(sDetailsBean.getListPic2_url()))
                    urls1.add(sDetailsBean.getListPic2_url());

                if (BasicTool.isNotEmpty(sDetailsBean.getListPic3_url()))
                    urls1.add(sDetailsBean.getListPic3_url());

                if (BasicTool.isNotEmpty(sDetailsBean.getListPic4_url()))
                    urls1.add(sDetailsBean.getListPic4_url());

                if (BasicTool.isNotEmpty(sDetailsBean.getListPic5_url()))
                    urls1.add(sDetailsBean.getListPic5_url());

                blShopBanner.setViewUrls(urls1, null);
//                tvWineType.setText(sDetailsBean.getClassName());
                categay = sDetailsBean.getClassName();


                tvWineType.setText(sDetailsBean.getClassName());
                tv_region.setText(sDetailsBean.getOriginName());
                tv_chateau.setText(sDetailsBean.getChateauName());
                tv_leavel.setText(sDetailsBean.getChateauLeval());
                tv_year.setText(sDetailsBean.getYears());
                tv_alcoholic_strength.setText(sDetailsBean.getAlcoholDegree());
                tv_grape_variety.setText(sDetailsBean.getGrapeVarietiesNames());
                tv_colour_lustre.setText(sDetailsBean.getColour());
                tv_fragrance.setText(sDetailsBean.getSmell());

                tv_wine_Body.setText(sDetailsBean.getWineNames());
                tv_taste.setText(sDetailsBean.getTextureNames());
                tv_Capacity_specification.setText(sDetailsBean.getCapacity());
                tv_Storage_conditions.setText(sDetailsBean.getStorage());
                tv_Serving_Temp.setText(sDetailsBean.getDringkingTemperature());
                tv_Ingesting_time.setText(sDetailsBean.getSoberTime());
                tv_suggestion.setText(sDetailsBean.getCateringAdvise());
                tv_award.setText(sDetailsBean.getPrizes());
                tv_score.setText(sDetailsBean.getScore());

                mwvGraphicDetails.setHtml(sDetailsBean.getProductDescriptionH5());
                Log.d("print", "商品详情页的图文详情1:" + sDetailsBean.getProductDescriptionH5());

//                ProductDescriptionH5 = HtmlRegexpUtil.getTextHTML(sDetailsBean.getProductDescriptionH5());

                ProductDescriptionH5 = HtmlRegexpUtil.getTextHTMLToCellarDesc(sDetailsBean.getProductDescriptionH5());
                Log.d("print", "商品详情页的图文详情2:" + ProductDescriptionH5);

                mwvChateauHistoy.setHtml(sDetailsBean.getChateauHistory());
                ChateauHistory = sDetailsBean.getChateauHistory();
//                Log.d("print", "商品详情页的酒庄历史: " + sDetailsBean.getChateauHistory());

                tvShopName.setText(sDetailsBean.getDrinkingName());
                tvChateauNameEn.setText(sDetailsBean.getDrinkingNameEn());
                tvPrice.setText("￥" + Arith.get2Decimal(sDetailsBean.getLsPrice()));
                tvChateauName.setText(sDetailsBean.getChateauName());
                int stock = Integer.parseInt(sDetailsBean.getStock());
                tvLsStock.setText(stock + "瓶");
                cnivNum.setMinVal(1);
                cnivNum.setMax(stock);
                setCard();
                break;

        }

//        if (getActivity() != null && BaseApplication.currentMode == BaseApplication.LOCAL_BUY) {
//            ((LocalBuyActivity) getActivity()).dismissHUD();
//        } else
        if (getActivity() != null && BaseApplication.currentMode == BaseApplication.MEMBER_BUY || BaseApplication.currentMode == BaseApplication.FAST_BUY) {
            ((ShopListActivity) getActivity()).dismissHUD();
        }
    }

    /**
     * 这个地方在点击购买的时候需要考虑是那个模式购买的 点击购买的时候做个判定  可选择的模式有 本地购买  会员购买   以及快速购买
     *
     * @param view
     */

    @OnClick({R.id.iv_jiujiao_history, R.id.iv_text_pic, R.id.iv_close, R.id.btn_buy, R.id.rlyt_shop_tab, R.id.rl_card, R.id.rlyt_details_tab, R.id.rlyt_history_tab, R.id.btn_add_to_card})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                //TODO  判断模式
//                if (BaseApplication.currentMode == BaseApplication.LOCAL_BUY) {
//                    if (getActivity() != null) {
//                        ((LocalBuyActivity) getActivity()).closeShopDetails();
//                        ShopDetailsFragment.this.onDestroy();
//                    }
//                } else {
                if (getActivity() != null) {
                    ((ShopListActivity) getActivity()).closeShopDetails();
                    ShopDetailsFragment.this.onDestroy();
                }
//                }

                break;
            case R.id.rl_card:
                if (BaseData.getBaseData().getCardShops().size() > 0) {
                    Activitys.toShoppingCard(getActivity());
                } else {
                    T.showToCenter(getString(R.string.common_cart_is_null));
                }
                break;
            case R.id.btn_buy:
                if (sDetailsBean == null) return;
                if (Integer.parseInt(sDetailsBean.getStock()) == 0) {
                    T.showToCenter(getString(R.string.common_inventory));
                    return;
                }

                final StockBean stockBean = new StockBean();
                stockBean.setId(sDetailsBean.getId());
                stockBean.setMainPicUrl(sDetailsBean.getMainPic_url());
                stockBean.setLsPrice(sDetailsBean.getLsPrice());
                stockBean.setNum(cnivNum.getNum());
                stockBean.setDrinkingNameEn(sDetailsBean.getDrinkingNameEn());
                stockBean.setDrinkingName(sDetailsBean.getDrinkingName());

                Log.d("print", "BaseApplication.currentMode" + BaseApplication.currentMode);
                //TODO   选择购买方式的判定
                if (BaseApplication.currentMode == BaseApplication.MEMBER_BUY) {
                    if (identityCheckDialog == null) {
                        identityCheckDialog = new IdentityCheckDialog();
                        identityCheckDialog.setCheckResult(new IdentityCheckDialog.CheckResult() {
                            @Override
                            public void result(IdentityCheckResultBean bean) {
                                identityCheckDialog.dismiss();
                                if (getActivity() != null) {
                                    ((ShopListActivity) getActivity()).closeShopDetails();
                                }
                                Activitys.toUserCheckOrder(getActivity(), UserCheckOrderActivity.NOW_BUY, stockBean, bean.getBid());
                            }
                        });
                    }
                    identityCheckDialog.show(getActivity().getSupportFragmentManager(), "identityCheckDialog");
                    this.onDestroy();

                } else {
                    if (getActivity() != null) {
                        //((ShopListActivity) getActivity()).closeShopDetails();
                    }
                    Activitys.toUserCheckOrder(getActivity(), UserCheckOrderActivity.NOW_BUY, stockBean, "");
                    this.onDestroy();
                }
                break;
            case R.id.rlyt_shop_tab:
                changeTab(0);
                break;
            case R.id.rlyt_details_tab:
                changeTab(1);
                break;
            case R.id.rlyt_history_tab:
                changeTab(2);
                break;
            case R.id.btn_add_to_card:
                if (sDetailsBean == null) return;
                if (Integer.parseInt(sDetailsBean.getStock()) == 0) {
                    T.showToCenter(getString(R.string.common_inventory));
                    return;
                }

                if (getActivity() != null && cnivNum.getCurentVal() > 0) {
                    int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                    view.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                    ImageView ball = new ImageView(getContext());
                    ball.setImageResource(R.mipmap.card_anim_icon);
                    L.i("startLocation：" + startLocation[0] + "    " + startLocation[1]);

                    StockBean sbean = new StockBean();
                    sbean.setId(sDetailsBean.getId());
                    sbean.setMainPicUrl(sDetailsBean.getMainPic_url());
                    sbean.setDrinkingName(sDetailsBean.getDrinkingName());
                    sbean.setDrinkingNameEn(sDetailsBean.getDrinkingNameEn());
                    sbean.setLsPrice(sDetailsBean.getLsPrice());
                    sbean.setOriginName(sDetailsBean.getOriginName());
                    sbean.setChateauName(sDetailsBean.getChateauName());
                    sbean.setProductStock(sDetailsBean.getStock() + "");
                    //TODO  加入购物车 需要判断
                    ((ShopListActivity) getActivity()).addToCard(ball, startLocation, sbean, cnivNum.getNum());

                }
                break;
//            case R.id.iv_guige:
//                //规格参数specification
//                speakSpecification(categay, Bilingual, DetailsLevel, Taste, FromIngesting, FromTemperature, FromSuggest);
//                break;
            case R.id.iv_text_pic:
                //图文详请
                if (!TextUtils.isEmpty(ProductDescriptionH5)) {
                    speakTvPic(ProductDescriptionH5);
                } else {
                    speakTvPic("当前商品没有添加详细信息");
                }
                break;
            case R.id.iv_jiujiao_history:
                //酒庄历史
                if (!TextUtils.isEmpty(ChateauHistory)) {
                    speakHistory(ChateauHistory);
                } else {
                    speakTvPic("当前酒庄没有添加历史信息");
                }
                break;
        }
    }

    /**
     * 读规格参数
     */
    private void speakSpecification(String categay, String bilingual, String detailsLevel, String taste, String fromIngesting, String fromTemperature, String fromSuggest) {
        setParam();
        // 3.开始合成
        mTts.startSpeaking("品类是" + categay + "。" + "品牌中英文是" + bilingual + "。" + "酒庄级别为" + detailsLevel + "。"
                        + "口感分类建议为" + taste + "。" + "建议醒酒时间为" + fromIngesting + "。" + "适宜的温度是" + fromTemperature
                        + "。" + "建议配上" + fromSuggest + "一起食用"
                , mSynListener);

    }

    /**
     * 读酒庄历史
     *
     * @param text
     */
    private void speakHistory(String text) {
        setParam();
        mTts.startSpeaking(text, mSynListener);
    }

    //播放图文详情
    private void speakTvPic(String text) {
        setParam();
        mTts.startSpeaking(text, mSynListener);
    }

    private void setParam() {
        // 1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(getContext(), null);
        Log.d("print", "Speak: " + mTts);
        // 2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");// 设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");// 设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");// 设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); // 设置云端
        // 设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        // 保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
        // 如果不需要保存合成音频，注释该行代码
        // mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
        // 3.开始合成
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
            mTts.setParameter(SpeechConstant.PARAMS, null);
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }

    public void setCard() {
        if (tvShopNumber == null) {
            return;
        }
        int size = BaseData.getBaseData().getCardNums();
        if (size == 0) {
            tvShopNumber.setVisibility(View.INVISIBLE);
        } else if (size > 99) {
            tvShopNumber.setVisibility(View.VISIBLE);
            tvShopNumber.setText("99");
        } else {
            tvShopNumber.setVisibility(View.VISIBLE);
            tvShopNumber.setText(size + "");
        }
    }

    private void changeTab(int index) {
        tvShop.setTextColor(getResources().getColorStateList(R.color.color_66));
        tvDetails.setTextColor(getResources().getColorStateList(R.color.color_66));
        tvChateauTab.setTextColor(getResources().getColorStateList(R.color.color_66));
        vChateau.setVisibility(View.GONE);
        vDetailsLine.setVisibility(View.GONE);
        vShopLine.setVisibility(View.GONE);
        if (index == 0) {
            vShopLine.setVisibility(View.VISIBLE);
            tvShop.setTextColor(getResources().getColorStateList(R.color.black));
            svDetails.scrollTo(0, 0);
        } else if (index == 1) {
            vDetailsLine.setVisibility(View.VISIBLE);
            tvDetails.setTextColor(getResources().getColorStateList(R.color.black));
            svDetails.scrollTo(0, (int) llytDetailsImgTxt.getY());
        } else if (index == 2) {
            vChateau.setVisibility(View.VISIBLE);
            tvChateauTab.setTextColor(getResources().getColorStateList(R.color.black));
            svDetails.scrollTo(0, (int) llytChateauHistoy.getY());
        }
    }
}