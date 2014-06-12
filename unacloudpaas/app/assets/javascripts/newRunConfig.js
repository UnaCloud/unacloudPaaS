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
var selectedRoleId;
function addModule(roleId){
    selectedRoleId=roleId;
    $( "#selectModuleDlg" ).dialog("open");
}
$( document ).ready(function() {
    $( "[id^=spinner]" ).spinner();
    $( "#selectModuleDlg" ).dialog({
        autoOpen: false,
        modal: true
    });
    $( "#tags" ).autocomplete({
        source: function( request, response ) {$.getJSON( "/moduleSearch", {query: request.term}, response );},
        select: function( event, ui ) {
            var moduleId="rolModule_"+selectedRoleId+"_"+ui.item.id;
            $( "#selectModuleDlg" ).dialog("close");
            $( "#roleModules_"+selectedRoleId ).prepend(
                "<div id=\""+moduleId+"\">"+ui.item.id+" "+ui.item.name+'<input type="hidden" name="'+moduleId+'" value="'+ui.item.name+'"></div>'
            );
            return false;
        }
    }).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
        return $("<li>").append( "<a>" + item.name + "<br>&nbsp;&nbsp;" + item.description + "</a>" ).appendTo( ul );
    };
    $( "#tags" ).css( "zIndex", 1003 );
});