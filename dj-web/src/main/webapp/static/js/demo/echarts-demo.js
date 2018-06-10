$(function() {
	var lineChart = echarts.init(document.getElementById("echarts-line-chart"));
	var lineoption = {
		title: {
			text: '一周在线变化'
		},
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			data: ['设备', 'APP']
		},
		grid: {
			x: 40,
			x2: 40,
			y2: 24
		},
		calculable: true,
		xAxis: [{
			type: 'category',
			boundaryGap: false,
			data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
		}],
		yAxis: [{
			type: 'value',
			axisLabel: {
				formatter: '{value}'
			}
		}],
		series: [{
				name: '微信',
				type: 'line',
				data: [11, 11, 15, 13, 12, 13, 10],
				smooth: true,
				markPoint: {
					data: [{
							type: 'max',
							name: '最大值'
						},
						{
							type: 'min',
							name: '最小值'
						}
					]
				},
				//              markLine : {
				//                  data : [
				//                      {type : 'average', name: '平均值'}
				//                  ]
				//              }
			},
			{
				name: 'APP',
				type: 'line',
				data: [1, 0, 2, 5, 3, 2, 10],
				smooth: true,
				markPoint: {
					data: [{
						name: '周最低',
						value: -2,
						xAxis: 1,
						yAxis: -1.5
					}]
				},
				//              markLine : {
				//                  data : [
				//                      {type : 'average', name : '平均值'}
				//                  ]
				//              }
			}
		]
	};
	lineChart.setOption(lineoption);
	$(window).resize(lineChart.resize);

	var barChart = echarts.init(document.getElementById("echarts-bar-chart"));
	var baroption = {
		title: {
			text: '微信和APP'
		},
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			data: ['微信', 'APP']
		},
		grid: {
			x: 30,
			x2: 40,
			y2: 24
		},
		calculable: true,
		xAxis: [{
			type: 'category',
			data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
		}],
		yAxis: [{
			type: 'value'
		}],
		series: [{
				name: '微信',
				type: 'bar',
				data: [2, 5, 7, 23, 25, 76, 135, 162, 32, 20, 6, 3],
				markPoint: {
					data: [{
							type: 'max',
							name: '最大值'
						},
						{
							type: 'min',
							name: '最小值'
						}
					]
				},
				//              markLine : {
				//                  data : [
				//                      {type : 'average', name: '平均值'}
				//                  ]
				//              }
			},
			{
				name: 'APP',
				type: 'bar',
				data: [3, 6, 9, 26, 28, 70, 175, 182, 48, 18, 6, 2],
				markPoint: {
					data: [{
							name: '年最高',
							value: 182,
							xAxis: 7,
							yAxis: 183,
							symbolSize: 18
						},
						{
							name: '年最低',
							value: 2,
							xAxis: 11,
							yAxis: 3
						}
					]
				},
				//              markLine : {
				//                  data : [
				//                      {type : 'average', name : '平均值'}
				//                  ]
				//              }
			}
		]
	};
	barChart.setOption(baroption);

	window.onresize = barChart.resize;

});