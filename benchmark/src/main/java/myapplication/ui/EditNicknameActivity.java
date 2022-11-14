package myapplication.ui;

import android.view.View;

import org.litepal.LitePal;

import java.util.List;

import myapplication.base.BaseMessageActivity;
import myapplication.modules.login.LoginBean;
import myapplication.modules.login.LoginRequest;
import myapplication.modules.proxy.IPProxy;
import tgio.benchmark.R;
import tgio.benchmark.databinding.ActivityCheckAccountBinding;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.21 10:23
 */
public class EditNicknameActivity extends BaseMessageActivity<ActivityCheckAccountBinding> {
    private LoginRequest request;
    private static final String [] nicknames = {"管理", "财财", "财务", "主持管理"};
    String str = "美女客服，百家乐必胜，客服，八九点，大红，秘书，菜菜，财务，大总管，总管，总统，红包，福利，必胜，白马，老千腾飞，瓜瓜，保安许，小许，章百万，风吟，保安赵，老赵，美人的小秘书，红魔，伯爵，东北大哥，司机老李，小提琴\uD83C\uDFBB，红魔，会长，身骑白马逛中原，进群即送福利，靠谱正规群，操蛋的人生，八九不离手，只想红，李局长，旭日，凤凰老群，害羞有饭吃，操蛋，九点，八点也行，湖南人爱打牌，澳门人生，理赌人生，我的牌打的好，每天红三千，一年赢1000万，百家乐有意思，喜欢博，博一博，单车变摩托，百事可乐，油纸伞，1905，16888，管理员，管理，难道不可以天天红，电影，唐朝诗人，赵宇飞，腾飞卧槽你，瓜瓜呱呱，小许子，稳赢，稳稳，财富自由，澳门，勐拉，越南王，西港王，菠菜数据，操蛋人，恭喜发财，瓜子，能红，必胜客，自由搏击，村支书，北京拆迁户，看海，女人诱惑，诱惑的美人，濠江，永利皇宫，美狮，米高梅，美高梅，好难啊，疫情防控，旭日东升，赢赢赢，天天赢，长红，长發，长江，过滤嘴，如梦初醒，新东方，细水长流，聚沙成塔，网易云，莫斯科，和宝宝，三傻，稳稳稳，必须稳，不稳不行，九杀八，大江木业，包旺，巴黎圣母院，四方，金字塔，水晶，尊龙，张曼乐，犹豫斌哥，书记，镇长，你大爷，搏一搏，博雅儿，海钓，一往情深，杨文强，许文强，水滴石穿，洋葱头，大头，大头强，小号，大户人家，奥特曼，凹凸曼，小豆豆，豆豆，小朱宝，臭宝，打工买别墅，啊飞，阿朱，没事打豆豆，天选之人，广西刘德华，重庆吴彦祖，上海梅艳芳，比尔，乌克兰，叙利亚，三宝一，人生无悔，平常心，红豆，大少，一碗水，释义，九江张学友";
    @Override
    protected String getTitleStr() {
        return "批量修改昵称";
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_edit_nickname;
    }

    @Override
    protected void init() {
        request = new LoginRequest(this);
    }

    public void check(View view){
        loginAll();
    }

    public void loginAll() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                List<LoginBean> regs2 = LitePal.findAll(LoginBean.class);
                int count = 0;
                String [] nicknames = str.split("，");
                for (LoginBean bean : regs2){
                    sendTextMessage("开始设置"+bean.getNickname());
                    sendTextMessage("开始设置代理");
                    IPProxy.setProxy(context);
                    sendTextMessage("代理设置成功");
                    sendTextMessage("开始登录"+bean.getNickname());
                    LoginBean loginBean = request.login(bean.getUsername(), "666888aa..", bean.getDeviceid(), bean.getClientid());
                    if(loginBean == null || loginBean.isfeng()){
                        sendTextMessage("登录"+bean.getNickname()+"失败，原因："+request.getErrorMessage());
                    }else{
                        int userid = loginBean.getUser_id();
                        if(userid == -1){
                            sendTextMessage(bean.getNickname()+"已经被封，开始删除");
                            bean.delete();
                            count++;
                            sendTextMessage(bean.getNickname()+"删除完成");
                        }else{
                            sendTextMessage(loginBean.getUsername()+"登陆成功,状态正常");
                            String token = loginBean.getToken();

                            String nickname = nicknames[(int) (Math.random()*nicknames.length-1)];
                            boolean flag = request.editNickname(token, nickname);
                            if(flag){
                                sendTextMessage(bean.getNickname()+"修改昵称完成，修改为:"+nickname);
                            }else{
                                sendTextMessage(bean.getNickname()+"修改昵称失败，原因为:"+request.getErrorMessage());
                            }
                        }

                    }
                }
                sendDialogMessage("----------全部完成----------");

            }
        }.start();
    }
}
