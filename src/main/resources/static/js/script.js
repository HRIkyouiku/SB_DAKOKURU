document.addEventListener('DOMContentLoaded', function() {
    function updateTime() {
        const now = new Date();
        const year = now.getFullYear();
        const month = (now.getMonth() + 1).toString().padStart(2, '0');
        const day = now.getDate().toString().padStart(2, '0');
        const hours = now.getHours().toString().padStart(2, '0')
        const minutes = now.getMinutes().toString().padStart(2, '0')
        const seconds = now.getSeconds().toString().padStart(2, '0')
        const weekdays = [`日`,`月`,`火`,`水`,`木`,`金`,`土`]
        const dayofweeks = weekdays[now.getDay()]
        const dateString = `${year}/${month}/${day}(${dayofweeks})`;
        const timeString = `${hours}:${minutes}:${seconds}`;
        document.getElementById('date').textContent = dateString;
        document.getElementById('time').textContent = timeString;
    }
    updateTime();
    setInterval(updateTime, 1000);
});

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