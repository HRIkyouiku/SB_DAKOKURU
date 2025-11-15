//layout.html
$(function(){
    let $nav = $("#navigation"),
    $slideLine = $("#slide-line"),
    $currentItem = $(".current-item");
    // メニューにアクティブな項目がある場合
    if ($currentItem.length) {
        $slideLine.css({
            "width": $currentItem.width() + 10 + "px",
            "left": $currentItem.position().left + 5 + "px"
        });
    }
    // 下線のトランジション
    $nav.find("li").hover(
        function(){
            $slideLine.css({
                "width": $(this).width() + 10 + "px",
                "left": $(this).position().left + 5 + "px"
            });
        },
        function(){
            if ($currentItem.length) {
                // 現在の項目に戻す
                $slideLine.css({
                    "width": $currentItem.width() + 10 + "px",
                    "left": $currentItem.position().left + 5 + "px"
                });
            } else {
                // 非表示にする
                $slideLine.width(0);
            }
        }
    );
});

//templates/creat.html
$(function showTime(){
    let now = new Date();            // 現在日時取得
    let year = now.getFullYear();    // 年
    let month = ("00" + (now.getMonth()+1)).slice(-2);    // 月
    let day = ("00" + now.getDate()).slice(-2);           // 日
    let wday = now.getDay();         // 曜日の値
    let week = [ " (日)", " (月)", "(火)", "(水)", "(木)", "(金)", "(土)" ];

    let hour = ("00" + now.getHours()).slice(-2);	     // 時
    let min = ("00" + now.getMinutes()).slice(-2);      // 分
    let sec = ("00" + now.getSeconds()).slice(-2);      // 秒

    let ymd = year + "/" + month + "/" + day + week[wday];
    let hms = hour + ":" + min + ":" + sec;

    document.getElementById("ymd").innerHTML = ymd;
    document.getElementById("hms").innerHTML = hms;

    setInterval(showTime, 1000);
});
