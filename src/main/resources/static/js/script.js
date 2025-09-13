// リアルタイムデジタル時計の機能
document.addEventListener('DOMContentLoaded', () => {
    const dateDisplay = document.getElementById('date-display');
    const timeDisplay = document.getElementById('time-display');
    const timestampTimeInput = document.getElementById('timestamp-time');
    const timestampForm = document.getElementById('timestamp-form');
    
    function updateClock() {
        const now = new Date();
        // 日付のフォーマット (例: 2025/08/16 (土))
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');
        const weekday = ['日', '月', '火', '水', '木', '金', '土'][now.getDay()];
        const formattedDate = `${year}/${month}/${day} (${weekday})`;
        
        // 時刻のフォーマット (例: 14:30:05)
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');
        const seconds = String(now.getSeconds()).padStart(2, '0');
        const formattedTime = `${hours}:${minutes}:${seconds}`;
        
        // HTML要素に表示
        if (dateDisplay) {
            dateDisplay.textContent = formattedDate;
        }
        if (timeDisplay) {
            timeDisplay.textContent = formattedTime;
        }
        
        // 隠しフィールドに時刻をセット (YYYY-MM-DD HH:mm:ss形式)
        if (timestampTimeInput) {
            const formattedDateTime = `${year}-${month}-${day} ${formattedTime}`;
            timestampTimeInput.value = formattedDateTime;
        }
    }
    // 1秒ごとに時計を更新
    setInterval(updateClock, 1000);
    // 初回実行
    updateClock();
    
    // フォーム送信時の処理 (時刻を最終チェック)
    if (timestampForm) {
        timestampForm.addEventListener('submit', (event) => {
            updateClock(); // 送信直前に時刻を再取得
        });
    }
});

//以下、各ファイルのjs情報 (layout.html)
// jQueryを使用したナビゲーションバーのアニメーション
$(function() {
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
        function() {
            $slideLine.css({
                "width": $(this).width() + 10 + "px",
                "left": $(this).position().left + 5 + "px"
            });
        },
        function() {
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