(function ($) {
    $(document).ready(function () {

        //загрузка фото на клік
        uploadImage();
        dropAreaView();

        //Облок для перетягування
        function dropAreaView() {
            let dropArea = document.getElementById('pic');
            ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
                dropArea.addEventListener(eventName, preventDefaults, false)
            })

            function preventDefaults(e) {
                e.preventDefault()
                e.stopPropagation()
            }
            dropArea.addEventListener('drop', handleDrop, false)
            //console.log(dropArea);
            function handleDrop(e) {
                let dt = e.dataTransfer
                let files = dt.files
                let uploader = $('<input type="file" name="images[]" style="display:none"/>');

                uploader.files = files;
                //console.log(uploader);

                viewFileUploader(uploader);
            }
        }

        //загрузка фото на клік
        function uploadImage() {
            let button = $('.images .pic');
            button.on('click', function () {
                let uploader = $('<input type="file" name="images[]" style="display:none"/>');
                uploader.click();

                uploader.on('change', function () {
                    viewFileUploader(this);
                });
            });
            $("#tbodyFileList").on("click", ".removefile", function () {
                $(this).closest("tr").remove();
            });

        }

        function viewFileUploader(uploader) {

            var file = uploader.files[0];
            var nBytes = file.size;
            var sOutput = nBytes + " bytes";
            // optional code for multiples approximation
            for (var aMultiples = ["KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"], nMultiple = 0, nApprox = nBytes / 1024; nApprox > 1; nApprox /= 1024, nMultiple++) {
                sOutput = nApprox.toFixed(3) + " " + aMultiples[nMultiple];
            }

            var type = file.type;
            let fileTypes = {
                'wordprocessingml.document': '<i class="fa fa-2x fa-file-word-o text-primary"></i>',
                'spreadsheetml.sheet': '<i class="fa fa-2x fa-file-excel-o text-success"></i>',
                'presentationml.presentation': '<i class="fa fa-2x fa-file-powerpoint-o text-danger"></i>',
                'image': '<i class="fa fa-2x  fa-file-image-o text-warning"></i>',
                'pdf': '<i class="fa fa-2x fa-file-pdf-o text-danger"></i>',
                'compressed': '<i class="fa fa-2x fa-file-archive-o text-muted"></i>',
                'video': '<i class="fa fa-2x fa-file-video-o"></i>',
                'text': '<i class="fa fa-2x fa-file-text-o"></i>',
                'default': '<i class="fa fa-2x fa-file"></i>',
            };

            for (const [key, value] of Object.entries(fileTypes)) {
                if (type.includes(key)) {
                    type = value;
                    break;
                }
                if (key == 'default')
                    type = value;
            }

            let tr = $(`
            <tr>
                <td>
                	${type}
                </td>
                <td>${file.name}</td>
                <td>${sOutput}</td>
                <td><a href="#" class="btn btn-danger removefile"><i class="fa fa-times" aria-hidden="true"></i></a></td>
            </tr>
            `);
            tr.prepend(uploader);

            $("#tbodyFileList").prepend(tr);

        }
    });
})(jQuery);