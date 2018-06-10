$(function() {

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

function randomData(max) {
	if(max == undefined || max == null || max == "") {
		max = 1000;
	}
	return Math.round(Math.random() * 1000);
}