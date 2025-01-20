import * as echarts from 'echarts';

/**
 * 折线图表初始化
 * @param xTitleList X轴标题数据
 * @param seriesList 折线数据内容
 * @param legendList 图表数据说明
 * @param domId 目标DOM元素ID
 * @param xAxisName X轴刻度名称
 * @param gridRight 右侧边距
 */
export function initLineImageTable(xTitleList: any, seriesList: any, legendList: any, domId: any, xAxisName: any, gridRight: any) {
  const chartDom = document.getElementById(domId);
  let myChart = echarts.init(chartDom);
  if (myChart != null) {
    // 销毁老的图表实例
    myChart.dispose();
    myChart = echarts.init(chartDom)
  }
  let option = {
    backgroundColor: '#060b1e',
    title: {},
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: legendList,
      icon: "circle",
      textStyle: {
        color: '#69859b'
      },
      bottom: 'bottom',
    },
    grid: {
      top: '3%',
      left: '3%',
      right: gridRight,
      bottom: '30px',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      name: xAxisName,
      boundaryGap: false,
      data: xTitleList,
      axisLabel: {
        color: '#69859b'
      }
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        color: '#69859b'
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(70,70,70,0.5)'
        }
      }
    },
    series: seriesList
  };

  option && myChart.setOption(option);
}

/**
 * 数据集柱状图表初始化
 * @param dataSetSource 数据集数据
 * @param seriesList 柱形图表样式定义
 * @param domId 目标DOM元素ID
 * @param showTextLength X轴坐标刻度每行最大显示字数
 */
export function initDataSetBarImageTable(dataSetSource: any, seriesList: any, domId: any, showTextLength: any) {
  const chartDom = document.getElementById(domId);
  let myChart = echarts.init(chartDom);
  if (myChart != null) {
    // 销毁老的图表实例
    myChart.dispose();
    myChart = echarts.init(chartDom)
  }
  let option = {
    title: {},
    toolbox: {},
    legend: {
      textStyle: {
        color: '#69859b'
      },
      bottom: 'bottom',
      icon: "circle",
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      top: '3%',
      left: '3%',
      right: '3%',
      bottom: '30px',
      containLabel: true
    },
    dataset: {
      source: dataSetSource
    },
    xAxis: {
      type: 'category',
      axisLabel: {
        color: '#69859b',
        formatter: function (params: any) {
          let newParamsName = '';
          const paramsNameNumber = params.length; // 文字总长度
          const rowNumber = Math.ceil(paramsNameNumber / showTextLength);
          if (paramsNameNumber > showTextLength) {
            for (let p = 0; p < rowNumber; p++) {
              const start = p * showTextLength;
              const end = start + showTextLength;
              const tempStr = p === rowNumber - 1 ? params.substring(start, paramsNameNumber) : params.substring(start, end) + '\n';
              newParamsName += tempStr;
            }
          } else {
            newParamsName = params;
          }
          return newParamsName;
        },
      }
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        color: '#69859b'
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(70,70,70,0.5)'
        }
      }
    },
    series: seriesList
  };

  option && myChart.setOption(option);
}

/**
 * 堆叠柱状图表初始化
 * @param xTitleList X轴数据
 * @param seriesList 图表数据内容-包含堆叠数据
 * @param legendList 图表数据说明
 * @param domId 目标DOM元素ID
 */
export function initStackBarImageTable(xTitleList: any, seriesList: any, legendList: any, domId: any, showTextLength: any) {
  const chartDom = document.getElementById(domId);
  let myChart = echarts.init(chartDom);
  if (myChart != null) {
    // 销毁老的图表实例
    myChart.dispose();
    myChart = echarts.init(chartDom)
  }
  let option = {
    title: {},
    toolbox: {},
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: legendList,
      textStyle: {
        color: '#69859b'
      },
      bottom: 'bottom',
      icon: "circle",
    },
    grid: {
      top: '3%',
      left: '3%',
      right: '3%',
      bottom: '30px',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: xTitleList,
      axisLabel: {
        color: '#69859b',
        formatter: function (params: any) {
          let newParamsName = '';
          const paramsNameNumber = params.length; // 文字总长度
          const rowNumber = Math.ceil(paramsNameNumber / showTextLength);
          if (paramsNameNumber > showTextLength) {
            for (let p = 0; p < rowNumber; p++) {
              const start = p * showTextLength;
              const end = start + showTextLength;
              const tempStr = p === rowNumber - 1 ? params.substring(start, paramsNameNumber) : params.substring(start, end) + '\n';
              newParamsName += tempStr;
            }
          } else {
            newParamsName = params;
          }
          return newParamsName;
        },
      }
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        color: '#69859b'
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(70,70,70,0.5)'
        }
      }
    },
    series: seriesList
  };

  option && myChart.setOption(option);
}

/**
 * 条形图图表初始化
 * @param yAxisList Y轴刻度内容
 * @param seriesList X轴条形定义与数据定义
 * @param domId 目标DOM元素ID
 */
export function initStripBarImageTable(yAxisList: any, seriesList: any, domId: any) {
  const chartDom = document.getElementById(domId);
  let myChart = echarts.init(chartDom);
  if (myChart != null) {
    // 销毁老的图表实例
    myChart.dispose();
    myChart = echarts.init(chartDom)
  }
  let option = {
    title: {},
    toolbox: {},
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      textStyle: {
        color: '#69859b'
      },
      bottom: 'bottom',
      icon: "circle",
    },
    grid: {
      top: '3%',
      left: '3%',
      right: '3%',
      bottom: '30px',
      containLabel: true
    },
    xAxis: {
      type: 'value',
      axisLabel: {
        color: '#69859b'
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(70,70,70,0.5)'
        }
      }
    },
    yAxis: {
      type: 'category',
      axisLabel: {
        color: '#69859b'
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(70,70,70,0.5)'
        }
      },
      data: yAxisList
    },
    series: seriesList
  };

  option && myChart.setOption(option);
}

/**
 * 环形图图表初始化
 * @param seriesDataList 展示数据
 * @param graphicChildren 圆环内部文本
 * @param domId 目标DOM元素ID
 * @param seriesName 注解信息的名称
 */
export function initRingPieImageTable(seriesDataList: any, graphicChildren: any, domId: any, seriesName: any, maxTextNum: any) {
  const chartDom = document.getElementById(domId);
  let myChart = echarts.init(chartDom);
  if (myChart != null) {
    // 销毁老的图表实例
    myChart.dispose();
    myChart = echarts.init(chartDom)
  }
  const option = {
    tooltip: {
      trigger: 'item'
    },
    grid: {
      top: '3%',
      left: '3%',
      right: '3%',
      bottom: '3%',
      containLabel: true
    },
    series: [
      {
        name: seriesName,
        type: 'pie',
        radius: ['40%', '60%'],
        label: {
          color: 'inherit',
          position: 'outer',
          borderWidth: 0,
          fontSize: 12, // 缩小字体
          formatter: function (params) {
            const name = params.name;
            const percent = params.percent;
            let formattedName = '';
            for (let i = 0; i < name.length; i += maxTextNum) {
              // 如果是最后一行，则不添加换行符
              if (i + maxTextNum >= name.length) {
                formattedName += name.slice(i, i + maxTextNum);
              } else {
                formattedName += name.slice(i, i + maxTextNum) + '\n';
              }
            }
            return `${formattedName}:${percent}%`;
          }
        },
        data: seriesDataList
      }
    ],
    graphic: graphicChildren
  };

  option && myChart.setOption(option);
}

/**
 * 饼图图表初始化
 * @param seriesDataList 展示数据
 * @param domId 目标DOM元素ID
 * @param seriesName 注解信息的名称
 */
export function initPieImageTable(seriesDataList: any, domId: any, seriesName: any) {
  const chartDom = document.getElementById(domId);
  let myChart = echarts.init(chartDom);
  if (myChart != null) {
    // 销毁老的图表实例
    myChart.dispose();
    myChart = echarts.init(chartDom)
  }
  const option = {
    tooltip: {
      trigger: 'item'
    },
    grid: {
      top: '3%',
      left: '3%',
      right: '3%',
      bottom: '3%',
      containLabel: true
    },
    series: [
      {
        name: seriesName,
        type: 'pie',
        radius: '70%',
        label: {
          color: 'inherit',
          position: 'outer',
          borderWidth: 0,
          formatter: '{b}：{d}%'
        },
        data: seriesDataList
      }
    ],
  };

  option && myChart.setOption(option);
}

export const PieColorData = {
  "惠城区": "#726D86",
  "惠阳区": "#c8ceda",
  "博罗县": "#006FFF",
  "惠东县": "#9293AD",
  "龙门县": "#77F8FF",
  "大亚湾区": "#6DD400",
  "仲恺区": "#F7B500",
}

export const CompanyModelColorData = {
  "20人以下": "#726D86",
  "20-99人": "#c8ceda",
  "100-299人": "#006FFF",
  "300-499人": "#9293AD",
  "500-999人": "#77F8FF",
  "1000-9999人": "#6DD400",
  "10000人以上": "#F7B500",
}
