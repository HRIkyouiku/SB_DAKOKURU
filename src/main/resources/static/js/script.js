//時計機能

//数字が一桁の場合、先頭に0を追加する関数
function twoFormat(data) {
    let number;
    if (data < 10) {
        number = "0" + data;
    } else {
        number = data;
    }
    return number;
}

function clock() {
    //現在時刻
    const nowTime = new Date();
    //年
    const year = nowTime.getFullYear();
    //月
    const month = twoFormat(nowTime.getMonth() + 1);
    //日
    const day = twoFormat(nowTime.getDate());// 日(1–31)
    //曜日
    const weeks = ["日","月","火","水","木","金","土"];// 曜日用の配列
    const week = weeks[nowTime.getDay()];// 曜日(0-6)
    //時間
    const nowHour = twoFormat(nowTime.getHours());
    //分数
    const nowMinutes = twoFormat(nowTime.getMinutes());
    //秒数
    const nowSeconds = twoFormat(nowTime.getSeconds());
    
    const message = year + "/" + month + "/" + day + "(" + week + ")";
    const message2 = nowHour + ":" + nowMinutes + ":" + nowSeconds;
    document.getElementById("date").innerHTML = message;
    document.getElementById("time").innerHTML = message2;
}

//リアルタイム時間の表示
setInterval('clock()',1000);


//layout.htmlの記載部分

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