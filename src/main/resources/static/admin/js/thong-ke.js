function updateRegions() {
    var list = document.getElementById("list").value;
    var regionSelect = document.getElementById("region");

    // Xóa tất cả các tùyọn hiện có
    while (regionSelect.options.length > 0) {
        regionSelect.remove(0);
    }

    if (list === "all") {
        var regions = [""];
    } else if (list === "food") {
        var regions = foodList;
    } else if (list === "movie") {
        var regions = movieList;
    } else if (list === "schedule") {
        var regions = scheduleList;
    } else {
        var regions = ["Vui lòng chọn 1 mục"]; // Nếu không chọn mục nào
    }
    console.log()

    // Thêm các tùy chọn vào danh sách khu vực
    var option = document.createElement("option");
    option.text = 'Tất cmn cả';
    regionSelect.add(option);

    for (var i = 0; i < regions.length; i++) {
        var option = document.createElement("option");
        option.text = regions[i].name;
        option.value = regions[i].id;
        regionSelect.add(option);
    }
}

function updateChart() {
    var selectedType = document.getElementById("list").value;
    var selectedValue = document.getElementById("region").value;

    if(selectedType == 'food'){
        
    } else {

    }
}

var myChartThree = echarts.init(document.getElementById('myChartThree'));
var dataChartThreeX=[];
var dataChartThreeY=[];
for (var i = 0; i < revenueMovie.length; i++) {
    var totalMoneyMovie = revenueMovie[i][1]; // Access the total money
    var movieNameRe = revenueMovie[i][0]; // Access the cinema name
    dataChartThreeY.push(totalMoneyMovie);
    dataChartThreeX.push(movieNameRe);
}
optionThree = {
    xAxis: {
        type: 'category',
        data: dataChartThreeX
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            data: dataChartThreeY,
            type: 'bar'
        }
    ]
};

myChartThree.setOption(optionThree);
