//現在時間の取得
document.addEventListener('DOMContentLoaded', () => {
function nowDateTime() {
    const now = new Date();
    const yyyy = now.getFullYear();
    const mm = String(now.getMonth() + 1).padStart(2, '0');
    const dd = String(now.getDate()).padStart(2, '0');
    const hh = String(now.getHours()).padStart(2, '0');
    const min = String(now.getMinutes()).padStart(2, '0');
    const ss = String(now.getSeconds()).padStart(2, '0');

    const days = ['日', '月', '火', '水', '木', '金', '土'];
    const dayOfWeek = days[now.getDay()];

    const nowDateFormat = `${yyyy}/${mm}/${dd}（${dayOfWeek}）`;
    document.getElementById('date').textContent = nowDateFormat;
    const nowTimeFormat = `${hh}:${min}:${ss}`;
    document.getElementById('time').textContent = nowTimeFormat;
    }

    nowDateTime();
    setInterval(nowDateTime, 1000);
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