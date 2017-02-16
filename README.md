# Demo4Banner

```
<dependency>
  <groupId>com.linyuzai</groupId>
  <artifactId>banner</artifactId>
  <version>1.1.4</version>
  <type>pom</type>
</dependency>

compile 'com.linyuzai:banner:1.1.4'
```

>效果图看这里—>http://www.jianshu.com/p/4ee3feb2eeb6

```
<com.linyuzai.banner.Banner
  android:id="@+id/banner"
  android:layout_width="match_parent"
  android:layout_height="200dp"
  banner:auto_duration="750"
  banner:banner_interval="3000"
  banner:manual_duration="250"
  banner:stationary="false" />
```
|auto_duration|轮播时自动切换页面滚动时间,默认750ms|<br>
|banner_interval|自动轮播时间间隔,默认5000ms|<br>
|manual_duration|手动切换的页面滚动时间,默认250ms|<br>
|stationary|设置为true，则禁止手动切换页面,默认false|<br>
>然后是设置adapter，这里有两种adapter，BannerAdapter和BannerAdapter2。
下面是<b>adapter1</b>

```
mBanner.setAdapter(new BannerAdapter<ViewHolder>() {

            @Override
            public int getBannerCount() {
                return 0;
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent) {
                return null;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
             
            }

            @Override
            public boolean isLoop() {
                return true;
            }
        });
```
>最后一个isLoop()返回true表示可以无限循环，默认是false。
然后是<b>adapter2</b>

```
mBanner.setAdapter(new BannerAdapter2<ViewHolder>() {
            @Override
            public int getBannerCount() {
                return 0;
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent) {
                return null;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                
            }

            @Override
            public boolean isLoop() {
                return true;
            }

            @Override
            public boolean isChangeless() {
                return false;
            }
        });
```
>isChangeless()，默认false，这个方法用于数据更新，如果数据只是第一次创建的时候获取，之后不变动，那么该方法返回true可以减少消耗（或者用adapter1也OK），如果有下拉刷新之类的需要调用adapter.notifyDataSetChanged()那么默认的false就OK。
所以说，<b>BannerAdapter不能支持需要数据更新的情况，特别是count改变的情况，而BannerAdapter2可以</b>。
在使用BannerAdapter2进行adapter.notifyDataSetChanged()之后，还需要调用<b>mBanner.updateBannerAfterDataSetChanged();</b>调用之后页面返回到第一张。
当然上面所说的<b>支持数据更新是在设置为可以无限循环的前提下</b>。
设置完adapter之后，调用一下其中一个

```
public void startAutoScroll(long delay);

public void startAutoScroll();
```
>对于两种adapter，分别封装了一个简化版的adapter

```
mBanner.setAdapter(new ImageBannerAdapter() {
            @Override
            public void onImageViewCreated(ImageView view) {
                //view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }

            @Override
            public void onBindImage(ImageView image, int position) {
                
            }

            @Override
            public int getBannerCount() {
                return 0;
            }

            @Override
            public boolean isLoop() {
                return true;
            }
        });

mBanner.setAdapter(new ImageBannerAdapter2() {
            @Override
            public void onImageViewCreated(ImageView view) {
                //view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }

            @Override
            public void onBindImage(ImageView image, int position) {
                
            }

            @Override
            public int getBannerCount() {
                return 0;
            }

            @Override
            public boolean isLoop() {
                return true;
            }

            @Override
            public boolean isChangeless() {
                return false;
            }
        });
```
>另外还有一些其他的方法

```
public void stopAutoScroll();//停止自动播放

public void bindIndicator(Indicator indicator);//绑定导航栏，之后会讲到

public void setOnBannerItemClickListener(OnBannerItemClickListener listener);//item的点击事件

public void setOnBannerChangeListener(OnBannerChangeListener listener);//就是ViewPager.OnPageChangeListener
```
>简单来讲就是：<br>
1.设置adapter；<br>
2.调用startAutoScroll()。<br>

```
<com.linyuzai.banner.hint.HintBanner
  android:id="@+id/hint_banner"
  android:layout_width="match_parent"
  android:layout_height="200dp"
  hint:hint_auto_duration="750"
  hint:hint_banner_interval="3000"
  hint:hint_manual_duration="250" />
```
>三个属性对应Banner的三个属性，只是多了个前缀，没有stationary属性。
HintBanner相对于Banner只是多了类似指示器。adapter的设置和Banner完全一样。
设置完adapter之后，添加HintView，提供了三种Creator。

```
mHintBanner.setHintView(new HintViewCreator() {
            @Override
            public View getHintView(ViewGroup parent) {
                return null;
            }

            @Override
            public void onHintActive(View hint) {
                //当前页面相对position的HintView的设置
            }

            @Override
            public void onHintReset(View hint) {
                //切换页面时还原的上一个HintView的设置
            }

            @Override
            public ViewLocation getViewLocation() {
                return null;//返回HintView的整体位置
            }
        });

mHintBanner.setHintView(new ColorHintViewCreator() {
            @Override
            public int getHintActiveColor() {
                return Color.WHITE;//当前页面HintView的颜色
            }

            @Override
            public int getHintResetColor() {
                return Color.BLACK;//还原上一个HintView的颜色
            }

            @Override
            public boolean isRound() {
                return true;//是否是圆的，默认方的
            }

            @Override
            public int getViewHeight() {
                return 5;//高度
            }

            @Override
            public int getViewWidth() {
                return 5;//宽度
            }

            @Override
            public ViewLocation getViewLocation() {
                ViewLocation location = ViewLocation.getDefaultViewLocation();
                location.setMarginBottom(10);
                return location;//返回HintView的整体位置
            }

            @Override
            public int getSpacing() {
                return 0;//两个HintView的间距
            }
        });
mHintBanner.setHintView(new DrawableHintViewCreator() {
            @Override
            public Drawable getHintActiveDrawable() {
                return getResources().getDrawable(R.mipmap.xxx);//当前页面HintView的Drawable
            }

            @Override
            public Drawable getHintResetDrawable() {
                return getResources().getDrawable(R.mipmap.xxx);//还原上一个HintView的Drawable
            }

            @Override
            public int getDrawableHeight() {
                return 25;//ImageView的高度
            }

            @Override
            public int getDrawableWidth() {
                return 25;//ImageView的宽度
            }

            @Override
            public int getSpacing() {
                return 0;//两个HintView的间距
            }

            @Override
            public ImageView.ScaleType getImageScaleType(){
                return ImageView.ScaleType.CENTER_INSIDE;//填充方式，默认CENTER_INSIDE
            }
        });
```
>其中ViewLocation有这些方法

```
public static ViewLocation getDefaultViewLocation();//获得默认location，水平居中，竖直对齐底部

public void setVerticalGravity(VerticalGravity vertical);//竖直方向，CENTER, TOP, BOTTOM

public void setHorizontalGravity(HorizontalGravity horizontal);//水平方向，CENTER, RIGHT, LEFT

public void setMarginTop(int marginTop);

public void setMarginBottom(int marginBottom);

public void setMarginLeft(int marginLeft);

public void setMarginRight(int marginRight);
```
>简单来讲就是：<br>
1.设置adapter；<br>
2.设置HintView；<br>
3.调用startAutoScroll()。<br>

```
<com.linyuzai.banner.indicator.Indicator
  android:id="@+id/indicator"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:background="@android:color/white"
  indicator:banner_anim="true"
  indicator:cursor_anim="true"
  indicator:indicator_anim="true" />
```

|banner_anim|绑定Banner之后，页面切换是否有动画,默认true|<br>
|cursor_anim|设置Cursor之后，Cursor是否有动画,默认true|<br>
|indicator_anim|Indicator切换是否有动画,>默认true|<br>
>给Indicator设置adapter

```
mIndicator.setAdapter(new BaseIndicatorAdapter<ViewHolder>() {
            @Override
            public int getIndicatorCount() {
                return 0;//item数量
            }

            @Override
            public ViewHolder onCreateIndicatorViewHolder(ViewGroup parent) {
                return null;
            }

            @Override
            public void onBindIndicatorViewHolder(ViewHolder holder, int position) {

            }

            @Override
            public boolean isFitScreenWidth() {
                return false;//是否和屏幕一样宽，并且等分item宽度
            }
        });

mIndicator.setAdapter(new TextIndicatorAdapter() {
            @Override
            public void onBindText(TextView text, int position) {
                //ViewGroup.LayoutParams params = text.getLayoutParams();
                //params.width = 100;
                //params.height = 50;
                //text.setLayoutParams(params);
                //text.setText(TITLE[position]);
                //text.setTextColor(Color.GRAY);
            }

            @Override
            public int getIndicatorCount() {
                return 0;
            }
        });
```
>Indicator不用一定要和Banner配合使用，也<b>可以单独使用</b>。
其中<b>isFitScreenWidth()这个方法，默认false</b>(记得把banner_anim，cursor_anim，indicator_anim都设置为false，就能够瞬间切换。将Banner的stationary设为false，则可以禁止手动切换)。
可以选择设置Cursor

```
mIndicator.setCursor(new SimpleCursorCreator() {
            @Override
            public float getHeight() {
                return 0;//高度
            }

            @Override
            public int getColor() {
                return 0;//颜色
            }

            @Override
            public float getScale() {
                return 0;//默认和item一样宽，通过scale调整宽度
            }

            @Override
            public Paint.Cap getStyle() {
                return null;//可以圆弧或有角
            }

            @Override
            public ViewLocation getViewLocation() {
                return null;//只支持竖直位置设置，水平方向无效
            }
        });
```
>给Indicator设置OnIndicatorChangeCallback

```
indicator.setOnIndicatorChangeCallback(new OnIndicatorChangeCallback() {
            @Override
            public boolean interceptBeforeChange(int position) {
                return false;//在Indicator切换之前，可加入操作，返回true拦截Indicator，使之不切换
            }

            @Override
            public void onIndicatorRestore(ViewHolder holder) {
                //((TextView) holder.itemView).setTextColor(Color.GRAY);
                //还原
            }

            @Override
            public void onIndicatorChange(ViewHolder holder) {
                //((TextView) holder.itemView).setTextColor(Color.BLUE);
                //切换
            }
        });
```
>绑定Banner和Indicator，可以两个都绑定，也可以只绑定一个，<b>必须先设置两者的adapter</b>

```
mIndicator.bindBanner(mBanner);//点击Indicator，切换Banner
        
mBanner.bindIndicator(mIndicator);//切换Banner，切换Indicator
```
>又要简单的说了：<br>
1.给Banner设置adapter；<br>
2.给Indicator设置adapter；<br>
3.给Indicator设置Cursor（可选）；<br>
4.给Indicator设置OnIndicatorChangeCallback；<br>
5.绑定Banner和Indicator<br>
