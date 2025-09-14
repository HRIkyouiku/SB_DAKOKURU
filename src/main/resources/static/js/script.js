// --- layouts/layout.htmlより ---
// ヘッダーメニューの表示
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


// --- timestamps/create.htmlより ---
// 日時と時刻の表示
function updateClock() {
      
      const now = new Date();
      
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, '0');
      const day =String(now.getDate()).padStart(2,'0');
      
      const weekdays = ['日', '月', '火', '水', '木', '金', '土'];
      const weekday = weekdays[now.getDay()];
      
      const hours = String(now.getHours()).padStart(2, '0');
      const minutes = String(now.getMinutes()).padStart(2, '0');
      const seconds = String(now.getSeconds()).padStart(2, '0');
      
      const dateString = ` ${year}/${month}/${day} (${weekday})`;
      const timeString = ` ${hours}:${minutes}:${seconds}`;


      document.getElementById('datePart').textContent =  dateString;
      document.getElementById('timePart').textContent =  timeString;
}
      setInterval(updateClock, 1000); // 1秒ごとに更新
      updateClock(); // 初回表示

