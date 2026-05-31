

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
	
	const now = new Date();

	const year = now.getFullYear();
	const month = now.getMonth() + 1;
	const date = now.getDate();
	const day = now.getDay();

	const daysOfWeek = ['(日)', '(月)', '(火)', '(水)', '(木)', '(金)', '(土)'];

	const today = `${year}/${month}/${date}/${daysOfWeek[day]} `;
	console.log(today);
	document.querySelector(".calendar").textContent = today;

	function displayTime() {
		const time = new Date();
		const padZero = (value) => value.toString().padStart(2, "0");

		const hour = padZero(time.getHours());
		const minute = padZero(time.getMinutes());
		const second = padZero(time.getSeconds());

		const currentTime = `${hour}:${minute}:${second}`;
		document.querySelector(".clock").textContent = currentTime;
	}

	displayTime();
	setInterval(displayTime, 1000);
	
});

