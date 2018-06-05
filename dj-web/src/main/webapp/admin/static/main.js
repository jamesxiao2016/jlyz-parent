$(function() {
	showPie();
	//showChina();
	showWorld();

	$('.i-checks').on('ifChecked', function(event) { //ifCreated 事件应该在插件初始化之前绑定 
		//changeMenuType($(event.target).val());
		var radioValue = $(event.target).val();
		if(radioValue == 0) {
			showChina();
		} else {
			showWorld();
		}
	});

	$('.i-checks').iCheck({
		checkboxClass: 'icheckbox_square-green',
		radioClass: 'iradio_square-green',
	});
});

function showWorld() {
	$("#map-china").hide();
	$("#map-world").show();
	option = {
		title: {
			left: 'center',
			top: 'top'
		},
		tooltip: {
			trigger: 'item',
			formatter: function(params) {
//				var value = (params.value + '').split('.');
//				value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,') +
//					'.' + value[1]; 
				return params.name + ' : ' + params.value;
			}
		},
		visualMap: {
			min: 0,
			max: 1500000,
			text: ['高', '低'],
			//realtime: false,
			calculable: true,
			color: ['green', 'yellow', '#fc8675']
		},
		series: [{
			name: '销量分布',
			type: 'map',
			mapType: 'world',
			roam: true,
			itemStyle: {
				emphasis: {
					label: {
						show: true
					}
				}
			},
			data: worldData
		}]
	};

	var mapChart = echarts.init(document.getElementById('map-world'));
	mapChart.group = 'map-world';
	mapChart.setOption(option);

	mapChart.on('click', function(params) {
		if(params.data.name == "China") {
			//$("#mapradio0").click();
			 $("#mapradio0").iCheck('check');
		}
	});
}

//全国地图
function showChina() {
	$("#map-world").hide();
	$("#map-china").show();
	option = {
		title: {
			show: false,
			//text: '销量统计',
			//subtext: '纯属虚构',
			left: 'center'
		},
		tooltip: {
			trigger: 'item'
		},
		legend: {
			orient: 'vertical',
			left: 'left',
			data: ['SLM755', 'SLM757', 'SNM753']
		},
		visualMap: {
			min: 0,
			max: 2500,
			left: 'left',
			top: 'bottom',
			text: ['高', '低'], // 文本，默认为数值文本
			calculable: true,
			color: ['green', 'yellow', '#fc8675']
		},
		toolbox: {
			show: false,
			orient: 'vertical',
			left: 'right',
			top: 'center',
			feature: {
				dataView: {
					readOnly: false
				},
				restore: {},
				saveAsImage: {}
			}
		},
		series: [{
				name: 'SLM755',
				type: 'map',
				mapType: 'china',
				roam: false,
				label: {
					normal: {
						show: true
					},
					emphasis: {
						show: true
					}
				},
				data: data1
			},
			{
				name: 'SLM757',
				type: 'map',
				mapType: 'china',
				label: {
					normal: {
						show: true
					},
					emphasis: {
						show: true
					}
				},
				data: data2
			},
			{
				name: 'SNM753',
				type: 'map',
				mapType: 'china',
				label: {
					normal: {
						show: true
					},
					emphasis: {
						show: true
					}
				},
				data: data3
			}
		]
	};
	var mapChart = echarts.init(document.getElementById('map-china'));
	mapChart.group = 'map-china';
	mapChart.setOption(option);
}

function showPie() {
	var chartData = [{
			value: 1000,
			name: 'SLM755'
		},
		{
			value: 2000,
			name: 'SLM757'
		},
		{
			value: 3000,
			name: 'SNM753'
		},
		{
			value: 1000,
			name: 'SLB747'
		},
		{
			value: 4000,
			name: 'SLB748'
		},
		{
			value: 5000,
			name: 'SNB743'
		},
		{
			value: 2000,
			name: 'SLM755L'
		},
		{
			value: 10000,
			name: 'SLB760'
		},
		{
			value: 3000,
			name: 'SLB761'
		},
		{
			value: 1000,
			name: 'SLB741'
		}
	];
	option = {
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b}: {c} ({d}%)"
		},
		legend: {
			orient: 'vertical',
			x: 'left',
			data: ["SLM755", "SLM757", "SNM753", "SLB748", "SLB747", "SNB743", "SLM755L", "SLB760", "SLB761", "SLB741"]
		},
		series: [{
			name: '出货量',
			type: 'pie',
			radius: ['50%', '80%'],
			avoidLabelOverlap: false,
			label: {
				normal: {
					show: false,
					position: 'center'
				},
				emphasis: {
					show: true,
					textStyle: {
						fontSize: '26',
						fontWeight: 'bold'
					}
				}
			},
			labelLine: {
				normal: {
					show: false
				}
			},
			data: chartData
		}]
	};
	var myChart = echarts.init(document.getElementById('echarts-pie-chart'));
	myChart.setOption(option);
}

function showPie2() {
	option = {
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b}: {c} ({d}%)"
		},
		legend: {
			orient: 'vertical',
			x: 'left',
			data: ['直达', '营销广告', '搜索引擎', '邮件营销', '联盟广告', '视频广告', '百度', '谷歌', '必应', '其他']
		},
		series: [{
				name: '访问来源',
				type: 'pie',
				selectedMode: 'single',
				radius: [0, '30%'],

				label: {
					normal: {
						position: 'inner'
					}
				},
				labelLine: {
					normal: {
						show: false
					}
				},
				data: [{
						value: 335,
						name: '直达',
						selected: true
					},
					{
						value: 679,
						name: '营销广告'
					},
					{
						value: 1548,
						name: '搜索引擎'
					}
				]
			},
			{
				name: '访问来源',
				type: 'pie',
				radius: ['40%', '55%'],
				label: {
					normal: {
						formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
						backgroundColor: '#eee',
						borderColor: '#aaa',
						borderWidth: 1,
						borderRadius: 4,
						// shadowBlur:3,
						// shadowOffsetX: 2,
						// shadowOffsetY: 2,
						// shadowColor: '#999',
						// padding: [0, 7],
						rich: {
							a: {
								color: '#999',
								lineHeight: 22,
								align: 'center'
							},
							// abg: {
							//     backgroundColor: '#333',
							//     width: '100%',
							//     align: 'right',
							//     height: 22,
							//     borderRadius: [4, 4, 0, 0]
							// },
							hr: {
								borderColor: '#aaa',
								width: '100%',
								borderWidth: 0.5,
								height: 0
							},
							b: {
								fontSize: 16,
								lineHeight: 33
							},
							per: {
								color: '#eee',
								backgroundColor: '#334455',
								padding: [2, 4],
								borderRadius: 2
							}
						}
					}
				},
				data: [{
						value: 335,
						name: '直达'
					},
					{
						value: 310,
						name: '邮件营销'
					},
					{
						value: 234,
						name: '联盟广告'
					},
					{
						value: 135,
						name: '视频广告'
					},
					{
						value: 1048,
						name: '百度'
					},
					{
						value: 251,
						name: '谷歌'
					},
					{
						value: 147,
						name: '必应'
					},
					{
						value: 102,
						name: '其他'
					}
				]
			}
		]
	};
	var myChart = echarts.init(document.getElementById('echarts-pie-chart'));
	myChart.setOption(option);
}

function randomData(max) {
	if(max == undefined || max == null || max == "") {
		max = 1000;
	}
	return Math.round(Math.random() * 1000);
}