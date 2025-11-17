function setupNavigationUnderline() {
	let $nav = $("#navigation"),
		$slideLine = $("#slide-line"),
		$currentItem = $(".current-item");

	// メニューにアクティブな項目がある場合
	if ($currentItem.length) {
		$slideLine.css({
		width: $currentItem.width() + 10 + "px",
		left: $currentItem.position().left + 5 + "px"
    	});
	}

	// 下線のトランジション
	$nav.find("li").hover(
		function () {
			$slideLine.css({
	        width: $(this).width() + 10 + "px",
	        left: $(this).position().left + 5 + "px"
				});
	    },
	    function () {
			if ($currentItem.length) {
				// 現在の項目に戻す
				$slideLine.css({
				width: $currentItem.width() + 10 + "px",
				left: $currentItem.position().left + 5 + "px"
				});
			} else {
				// 非表示にする
				$slideLine.width(0);
			}
		}
	);
}

function showDate() {
	const area = document.getElementById("date");

	const currentDate = new Date();
  
	const date = currentDate.toLocaleDateString("ja-JP", {
	    year: "numeric",
	    month: "2-digit",
	    day: "2-digit",
	});
  
	const weekday = currentDate.toLocaleDateString("ja-JP", {
    	weekday: "short",
	});

	const formatted = `${date}` + `（${weekday}）`;
			
	area.textContent = `${formatted}`;
}

function showTime() {
	const area = document.getElementById("time");
	
	const time = new Date();
	const formatted = time.toLocaleTimeString("ja-JP", {
	    hour: "2-digit",
	    minute: "2-digit",
	    second: "2-digit"
	});
	
	area.textContent = `${formatted}`;
}

document.addEventListener("DOMContentLoaded", () => {
	setupNavigationUnderline();

	showDate();
 	showTime();
 	setInterval(showTime, 1000);
});
