function loadPlatform(id){
    $.getJSON( "/newRunPlatform?platformId=1", function( data ) {
        $("#platformName").html(data.name);
    });
}
function run(){
    alert($("#roles > tr").val())
    alert("run");
}
function handleFileSelect(evt) {
    $('#tfiles tr').slice(1).remove();
    var files = evt.target.files; // FileList object
    for (var i = 0, f; f = files[i]; i++) {
        $('#tfiles').append('<tr><td>'+escape(f.name)+'</td><td>'+(Math.floor(f.size/1024))+'KB</td><td><select name="fileType'+i+'" class="select"><option>Global</option><option>Rol</option><option>Local</option></select></td><td><input class="form-control" name="filePath'+i+'" value="/"></td></tr>');
    }
}