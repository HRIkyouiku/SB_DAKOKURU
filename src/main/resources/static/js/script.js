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
	showDate();
 	showTime();
 	setInterval(showTime, 1000);
});
