function handleSubmit(action) {
    const confirmed = confirm(action === 'update' ? '更新します。宜しいですか？' : '削除します。宜しいですか？');
    if (confirmed) {
        const form = document.getElementById("departmentForm");
        const departmentId = document.getElementById("departmentId").value;

        let url = "";
        if (action === 'update') {
            url = "/department/update/" + departmentId;
        } else if (action === 'delete') {
            url = "/department/delete/" + departmentId;
        }

        form.action = url;
        return true;
    }
    return false;
}