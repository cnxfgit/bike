// 百度地图API功能
var map = new BMap.Map("allmap"); // 创建Map实例
map.centerAndZoom(new BMap.Point(112.490, 34.565), 15); // 初始化地图,设置中心点坐标和地图级别

map.setCurrentCity("洛阳"); // 设置地图显示的城市 此项是必须设置的
map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放

// 添加带有定位的导航控件
var navigationControl = new BMap.NavigationControl({
	// 靠左上角位置
	anchor : BMAP_ANCHOR_TOP_LEFT,
	// LARGE类型
	type : BMAP_NAVIGATION_CONTROL_LARGE,
	// 启用显示定位
	enableGeolocation : true
});
map.addControl(navigationControl);
// 添加定位控件
var geolocationControl = new BMap.GeolocationControl();
geolocationControl.addEventListener("locationSuccess", function(e) {
	// 定位成功事件
	var address = '';
	address += e.addressComponent.province;
	address += e.addressComponent.city;
	address += e.addressComponent.district;
	address += e.addressComponent.street;
	address += e.addressComponent.streetNumber;
	alert("当前定位地址为：" + address);
});
geolocationControl.addEventListener("locationError", function(e) {
	// 定位失败事件
	alert(e.message);
});
map.addControl(geolocationControl);

// 112.48461, 34.555576 东山石窟 112.48346, 34.56717龙门博物馆
// 112.486838, 34.562562 观澜亭 东山石窟 112.483388, 34.559144万佛洞
// 龙门石窟四个标注
var pt1 = new BMap.Point(112.48461, 34.555576);
var myIcon1 = new BMap.Icon("../images/地图坐标绿.png", new BMap.Size(50,50));
var marker1 = new BMap.Marker(pt1,{icon:myIcon1});  // 创建标注
map.addOverlay(marker1);

var pt2 = new BMap.Point(112.48346, 34.56717);
var myIcon2 = new BMap.Icon("../images/地图坐标绿.png", new BMap.Size(50,50));
var marker2 = new BMap.Marker(pt2,{icon:myIcon2});  // 创建标注
map.addOverlay(marker2);

var pt3 = new BMap.Point(112.486838, 34.562562);
var myIcon3 = new BMap.Icon("../images/地图坐标绿.png", new BMap.Size(50,50));
var marker3 = new BMap.Marker(pt3,{icon:myIcon3});  // 创建标注
map.addOverlay(marker3);

var pt4 = new BMap.Point(112.483388, 34.559144);
var myIcon4 = new BMap.Icon("../images/地图坐标绿.png", new BMap.Size(50,50));
var marker4 = new BMap.Marker(pt4,{icon:myIcon4});  // 创建标注
map.addOverlay(marker4);