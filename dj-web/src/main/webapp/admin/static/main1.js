window.onload = function() {
	showBar();
	showLine();
	showPie();
	showPie2();
	showArea();
	showMap();
};

function showLine() {
	var config = {
		type: 'line',
		data: {
			labels: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月"],
			datasets: [{
				label: "设备",
				backgroundColor: window.chartColors.red,
				borderColor: window.chartColors.red,
				data: [
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor()
				],
				fill: false,
			}, {
				label: "APP",
				fill: false,
				backgroundColor: window.chartColors.blue,
				borderColor: window.chartColors.blue,
				data: [
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor()
				],
			}]
		},
		options: {
			responsive: true,
			title: {
				display: false,
				//text: 'Chart.js Line Chart'
			},
			tooltips: {
				mode: 'index',
				intersect: false,
			},
			hover: {
				mode: 'nearest',
				intersect: true
			},
			scales: {
				xAxes: [{
					display: true,
					scaleLabel: {
						display: true,
						labelString: '2017'
					}
				}],
				yAxes: [{
					display: true,
					scaleLabel: {
						display: false,
						labelString: 'Value'
					}
				}]
			}
		}
	};

	var lineCtx = document.getElementById("monthLineChart").getContext("2d");
	window.myLine = new Chart(lineCtx, config);

}

function showPie() {
	var config = {
		type: 'doughnut',
		data: {
			datasets: [{
				data: [
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
				],
				backgroundColor: [
					window.chartColors.red,
					window.chartColors.orange,
					window.chartColors.yellow,
					window.chartColors.green,
					window.chartColors.blue,
				],
				label: 'Dataset 1'
			}],
			labels: [
				"SNB743",
				"SLM753",
				"SLM755",
				"SLM760",
				"SLb761"
			]
		},
		options: {
			responsive: true,
			legend: {
				position: 'top',
			},
			title: {
				display: false,
				text: '产品统计'
			},
			animation: {
				animateScale: true,
				animateRotate: true
			}
		}
	};
	var pieCtx = document.getElementById("prodPieChart").getContext("2d");
	window.myDoughnut = new Chart(pieCtx, config);
}

function showPie2() {
	var config = {
		type: 'doughnut',
		data: {
			datasets: [{
				data: [
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
				],
				backgroundColor: [
					window.chartColors.red,
					window.chartColors.orange,
					window.chartColors.green,
					window.chartColors.blue,
				],
				label: 'Dataset 1'
			}],
			labels: [
				"深圳",
				"上海",
				"西安",
				"武汉"
			]
		},
		options: {
			responsive: true,
			legend: {
				position: 'top',
			},
			title: {
				display: false,
				text: '产品统计'
			},
			animation: {
				animateScale: true,
				animateRotate: true
			}
		}
	};
	var pieCtx = document.getElementById("cpyPieChart").getContext("2d");
	window.myDoughnut = new Chart(pieCtx, config);
}

function showBar() {
	var barChartData = {
		labels: ["SLM755", "SLM757", "SNM753", "SLB748", "SLB747", "SNB743", "SLM755L", "SLB760", "SLB761", "SLB741"],
		datasets: [{
			label: '已激活',
			backgroundColor: window.chartColors.green,
			data: [
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor()
			]
		}, {
			label: '未激活',
			backgroundColor: window.chartColors.gray,
			data: [
				randomScalingFactor(100),
				randomScalingFactor(90),
				randomScalingFactor(80),
				randomScalingFactor(70),
				randomScalingFactor(60),
				randomScalingFactor(50),
				randomScalingFactor(60),
				randomScalingFactor(70),
				randomScalingFactor(80),
				randomScalingFactor(90)
			]
		}]

	};
	var ctx = document.getElementById("salesBarChart").getContext("2d");
	window.myBar = new Chart(ctx, {
		type: 'bar',
		data: barChartData,
		options: {
			title: {
				display: false,
				text: "Chart.js Bar Chart - Stacked"
			},
			tooltips: {
				mode: 'index',
				intersect: false
			},
			responsive: true,
			scales: {
				xAxes: [{
					stacked: true,
				}],
				yAxes: [{
					stacked: true
				}]
			}
		}
	});
}

function showBar2() {
	var color = Chart.helpers.color;
	var barChartData = {
		labels: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月"],
		datasets: [{
			label: 'SLM755',
			backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
			borderColor: window.chartColors.red,
			borderWidth: 1,
			data: [
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor()
			]
		}, {
			label: 'SLM757',
			backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
			borderColor: window.chartColors.blue,
			borderWidth: 1,
			data: [
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor()
			]
		}]

	};
	var ctx = document.getElementById("salesBarChart").getContext("2d");
	window.myBar = new Chart(ctx, {
		type: 'bar',
		data: barChartData,
		options: {
			responsive: true,
			legend: {
				position: 'top',
			},
			title: {
				display: false,
				text: 'Chart.js Bar Chart'
			}
		}
	});
}

function showArea() {
	var presets = window.chartColors;
	var utils = Samples.utils;
	var inputs = {
		min: 20,
		max: 1000,
		count: 11,
		decimals: 2,
		continuity: 1
	};

	function generateData() {
		return utils.numbers(inputs);
	}

	function generateLabels(config) {
		return utils.months({
			count: inputs.count
		});
	}

	utils.srand(42);
	//"SLM755", "SLM757", "SNM753", "SLB748", "SLB747", "SNB743", "SLM755L", "SLB760", "SLB761", "SLB741"
	var data = {
		labels: generateLabels(),
		datasets: [{
			backgroundColor: utils.transparentize(presets.red),
			borderColor: presets.red,
			data: generateData(),
			//hidden: true,
			label: 'SLM755'
		}, {
			backgroundColor: utils.transparentize(presets.orange),
			borderColor: presets.orange,
			data: generateData(),
			label: 'SLM757',
			//fill: '-1'
		}, {
			backgroundColor: utils.transparentize(presets.yellow),
			borderColor: presets.yellow,
			data: generateData(),
			//hidden: true,
			label: 'SNM753',
			fill: 1
		}, {
			backgroundColor: utils.transparentize(presets.green),
			borderColor: presets.green,
			data: generateData(),
			label: 'SLB748',
			//fill: '-1'
		}, {
			backgroundColor: utils.transparentize(presets.blue),
			borderColor: presets.blue,
			data: generateData(),
			label: 'SLB747',
			//fill: '-1'
		}, {
			backgroundColor: utils.transparentize(presets.grey),
			borderColor: presets.grey,
			data: generateData(),
			label: 'SNB743',
			//fill: '+2'
		}, {
			backgroundColor: utils.transparentize(presets.purple),
			borderColor: presets.purple,
			data: generateData(),
			label: 'SLM755L',
			//fill: false
		}, {
			backgroundColor: utils.transparentize(presets.red),
			borderColor: presets.red,
			data: generateData(),
			label: 'SLB760',
			//fill: 8
		}, {
			backgroundColor: utils.transparentize(presets.orange),
			borderColor: presets.orange,
			data: generateData(),
			hidden: true,
			label: 'SLB741',
			//fill: 'end'
		}]
	};

	var options = {
		maintainAspectRatio: false,
		spanGaps: false,
		elements: {
			line: {
				tension: 0.4
			}
		},
		scales: {
			yAxes: [{
				stacked: true
			}]
		},
		plugins: {
			filler: {
				propagate: true
			},
			samples_filler_analyser: {
				target: 'chart-analyser'
			}
		}
	};

	var chart = new Chart('areaChart', {
		type: 'line',
		data: data,
		options: options
	});
}

function showMap() {
	var mapChart = echarts.init(document.getElementById("echarts-map-chart"));
	var mapoption = {
		title: {
			text: '销量统计',
			//subtext: '纯属虚构',
			x: 'center'
		},
		tooltip: {
			trigger: 'item'
		},
		legend: {
			orient: 'vertical',
			x: 'left',
			data: ['SLM755', 'SLM757', 'SNM753']
		},
		dataRange: {
			min: 0,
			max: 2500,
			x: 'left',
			y: 'bottom',
			text: ['高', '低'], // 文本，默认为数值文本
			calculable: true
		},
		toolbox: {
			show: false,
			orient: 'vertical',
			x: 'right',
			y: 'center',
			feature: {
				mark: {
					show: true
				},
				dataView: {
					show: true,
					readOnly: false
				},
				restore: {
					show: true
				},
				saveAsImage: {
					show: true
				}
			}
		},
		roamController: {
			show: true,
			x: 'right',
			mapTypeControl: {
				'china': true
			}
		},
		series: [{
				name: 'SLM755',
				type: 'map',
				mapType: 'china',
				roam: false,
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					},
					emphasis: {
						label: {
							show: true
						}
					}
				},
				data: [{
						name: '北京',
						value: randomData()
					},
					{
						name: '天津',
						value: randomData()
					},
					{
						name: '上海',
						value: randomData()
					},
					{
						name: '重庆',
						value: randomData()
					},
					{
						name: '河北',
						value: randomData()
					},
					{
						name: '河南',
						value: randomData()
					},
					{
						name: '云南',
						value: randomData()
					},
					{
						name: '辽宁',
						value: randomData()
					},
					{
						name: '黑龙江',
						value: randomData()
					},
					{
						name: '湖南',
						value: randomData()
					},
					{
						name: '安徽',
						value: randomData()
					},
					{
						name: '山东',
						value: randomData()
					},
					{
						name: '新疆',
						value: randomData()
					},
					{
						name: '江苏',
						value: randomData()
					},
					{
						name: '浙江',
						value: randomData()
					},
					{
						name: '江西',
						value: randomData()
					},
					{
						name: '湖北',
						value: randomData()
					},
					{
						name: '广西',
						value: randomData()
					},
					{
						name: '甘肃',
						value: randomData()
					},
					{
						name: '山西',
						value: randomData()
					},
					{
						name: '内蒙古',
						value: randomData()
					},
					{
						name: '陕西',
						value: randomData()
					},
					{
						name: '吉林',
						value: randomData()
					},
					{
						name: '福建',
						value: randomData()
					},
					{
						name: '贵州',
						value: randomData()
					},
					{
						name: '广东',
						value: randomData()
					},
					{
						name: '青海',
						value: randomData()
					},
					{
						name: '西藏',
						value: randomData()
					},
					{
						name: '四川',
						value: randomData()
					},
					{
						name: '宁夏',
						value: randomData()
					},
					{
						name: '海南',
						value: randomData()
					},
					{
						name: '台湾',
						value: randomData()
					},
					{
						name: '香港',
						value: randomData()
					},
					{
						name: '澳门',
						value: randomData()
					}
				]
			},
			{
				name: 'SLM757',
				type: 'map',
				mapType: 'china',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					},
					emphasis: {
						label: {
							show: true
						}
					}
				},
				data: [{
						name: '北京',
						value: randomData()
					},
					{
						name: '天津',
						value: randomData()
					},
					{
						name: '上海',
						value: randomData()
					},
					{
						name: '重庆',
						value: randomData()
					},
					{
						name: '河北',
						value: randomData()
					},
					{
						name: '安徽',
						value: randomData()
					},
					{
						name: '新疆',
						value: randomData()
					},
					{
						name: '浙江',
						value: randomData()
					},
					{
						name: '江西',
						value: randomData()
					},
					{
						name: '山西',
						value: randomData()
					},
					{
						name: '内蒙古',
						value: randomData()
					},
					{
						name: '吉林',
						value: randomData()
					},
					{
						name: '福建',
						value: randomData()
					},
					{
						name: '广东',
						value: randomData()
					},
					{
						name: '西藏',
						value: randomData()
					},
					{
						name: '四川',
						value: randomData()
					},
					{
						name: '宁夏',
						value: randomData()
					},
					{
						name: '香港',
						value: randomData()
					},
					{
						name: '澳门',
						value: randomData()
					}
				]
			},
			{
				name: 'SNM753',
				type: 'map',
				mapType: 'china',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					},
					emphasis: {
						label: {
							show: true
						}
					}
				},
				data: [{
						name: '北京',
						value: randomData()
					},
					{
						name: '天津',
						value: randomData()
					},
					{
						name: '上海',
						value: randomData()
					},
					{
						name: '广东',
						value: randomData()
					},
					{
						name: '台湾',
						value: randomData()
					},
					{
						name: '香港',
						value: randomData()
					},
					{
						name: '澳门',
						value: randomData()
					}
				]
			}
		]
	};
	mapChart.setOption(mapoption);
	$(window).resize(mapChart.resize);
}

function randomData(max) {
	if(max == undefined || max == null || max == "") {
		max = 1000;
	}
	return Math.round(Math.random() * 1000);
}