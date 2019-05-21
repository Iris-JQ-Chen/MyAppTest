package com.example.myapptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {

    private Drawer mainDrawer = null;
    private Toolbar mToolbar = null

    private void initSlidingDrawer(Bundle savedInstanceState) {
        // Handle Toolbar
        mainDrawer = new DrawerBuilder()
                .withActivity(this)
                //跟drawerlayout用法相同，用这个drawer替换覆盖掉原来drawerlayout的位置
                .withRootView(R.id.drawer_container)
                .withHeader(R.layout.view_drawer_header)
                .withHeaderDivider(false)
                .withSavedInstance(savedInstanceState)
                //与toolbar关联起来
                .withToolbar(mToolbar)
                //启用toolbar的ActionBarDrawerToggle动画
                .withActionBarDrawerToggleAnimated(true)
                .withDisplayBelowStatusBar(false)
                .withTranslucentStatusBar(false)
                .withDrawerLayout(R.layout.material_drawer)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("需求信息").withIcon(R.mipmap.ic_assignment_turned_in_black_48dp).withIdentifier(1),
                        new PrimaryDrawerItem().withName("我的商品").withIcon(R.mipmap.ic_local_grocery_store_black_48dp).withIdentifier(2),
                        new PrimaryDrawerItem().withName("我的消息").withIcon(R.mipmap.ic_chat_bubble_outline_black_48dp).withIdentifier(3),
                        new PrimaryDrawerItem().withName("设置").withIcon(R.mipmap.ic_build_black_48dp).withIdentifier(4)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            switch ((int)drawerItem.getIdentifier()) {
                                case 1:
                                    Toast.makeText(MainActivity.this,"1，需求信息",Toast.LENGTH_SHORT).show();
//                                    setToolBarTitle("需求信息");
//                                    fragUtils.showFragment(Tag_DemandProductListFrag);
                                    break;
                                case 2:
                                    Toast.makeText(MainActivity.this,"1，需求信息",Toast.LENGTH_SHORT).show();
//                                    setToolBarTitle("我的商品");
//                                    fragUtils.showFragment(Tag_SupplyProductListFrag);
                                    break;
                                case 3:
                                    Toast.makeText(MainActivity.this,"1，需求信息",Toast.LENGTH_SHORT).show();
//                                    setToolBarTitle("我的消息");
//                                    fragUtils.showFragment(Tag_MyMessageListFrag);
                                    break;
                                case 4:
                                    Toast.makeText(MainActivity.this,"1，需求信息",Toast.LENGTH_SHORT).show();
//                                    setToolBarTitle("设置");
//                                    fragUtils.showFragment(Tag_SettingFrag);
                                    break;
                                default:
                                    break;
                            }
                        }
                        return false;
                    }
                }).build();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = mainDrawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSlidingDrawer(savedInstanceState);
    }
}
