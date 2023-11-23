var selects = document.querySelectorAll('select');

selects.forEach(function(select) {
  select.addEventListener('focus', function() {
    select.size = 5;
    select.classList.add('fadeIn');
    select.classList.remove('fadeOut');
    select.style.backgroundColor = '#FFF';
  });

  select.addEventListener('blur', function() {
    select.size = 1;
    select.classList.add('fadeOut');
    select.classList.remove('fadeIn');
    select.style.backgroundColor = '#FFF';
  });

  select.addEventListener('change', function() {
    select.size = 1;
    select.blur();
    select.style.backgroundColor = '#FFF';
  });

  select.addEventListener('mouseover', function() {
    if(select.size === 1) {
      select.style.backgroundColor = 'rgb(247, 247, 247)';
    }
  });

  select.addEventListener('mouseout', function() {
    if(select.size === 1) {
      select.style.backgroundColor = '#FFF';
    }
  });
});

var currentDate = new Date();
var currentDay = currentDate.getDate();
var currentMonth = currentDate.getMonth() + 1;
var currentYear = currentDate.getFullYear();

var sevenDays = [];

for (var i = 0; i < 7; i++) {
  var nextDay = new Date(currentYear, currentMonth - 1, currentDay + i);

  var dateArray = [nextDay.getDate(), nextDay.getMonth() + 1,nextDay.getFullYear() ]; // Mảng chứa năm, tháng và ngày
  dateArray = dateArray.map(function(item) { // Chuyển đổi các phần tử trong mảng thành chuỗi và kiểm tra xem có cần thêm số 0 ở phía trước không
    return (item < 10 ? "0" : "") + item;
  });

  var dateString = dateArray.join('/'); // Xây dựng lại chuỗi ngày tháng

  sevenDays.push(dateString);
}

var selectElement = document.getElementById('startAt');

for (var j = 0; j < sevenDays.length; j++) {
  var optionElement = document.createElement('option');
  optionElement.value = sevenDays[j];
  optionElement.textContent = sevenDays[j];
  selectElement.appendChild(optionElement);
};
// var options = document.getElementById("startAt").getElementsByTagName("option"); // Lấy ra tất cả các tùy chọn
// var daysOfWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']; // Mảng các ngày trong tuần
//
// for (var i = 0; i < options.length; i++) {
//   var dateValue = options[i].value;
//   var parts = dateValue.split('/'); // Tách chuỗi ngày tháng năm thành mảng
//   var formattedDate = new Date(parts[2] + '-' + parts[1] + '-' + parts[0]); // Chuyển đổi thành đối tượng ngày
//
//   var dayOfWeek = daysOfWeek[formattedDate.getDay()]; // Lấy ra ngày trong tuần
//   var newDateString = dayOfWeek + ", " + formattedDate.getDate() + '/' +  (formattedDate.getMonth() + 1) + '/' + formattedDate.getFullYear(); // Format lại chuỗi ngày thành "Day, mm/dd/yyyy"
//
//   options[i].text = newDateString; // Gán giá trị mới vào tùy chọn
//   options[i].value = newDateString;
// }

var gioElements = document.querySelectorAll("#gio");

gioElements.forEach(function(element) {
  var myData = element.textContent;
  var dateTime = new Date(myData);
  var hour = dateTime.getHours(); // Lấy giờ (0-23)
  var formattedHour = String(hour).padStart(2, '0');
  var minute = dateTime.getMinutes();
  var formattedMinute = String(minute).padStart(2, '0');
  var formattedTime = formattedHour + ":" + formattedMinute;
  element.innerHTML = formattedTime;
})
