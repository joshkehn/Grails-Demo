package listscrubber

class UploadedFileController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [uploadedFileInstanceList: UploadedFile.list(params), uploadedFileInstanceTotal: UploadedFile.count()]
    }

    def create = {
        def uploadedFileInstance = new UploadedFile()
        uploadedFileInstance.properties = params
        return [uploadedFileInstance: uploadedFileInstance]
    }

    def save = {
        def uploadedFileInstance = new UploadedFile(params)
        if (uploadedFileInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'uploadedFile.label', default: 'UploadedFile'), uploadedFileInstance.id])}"
            redirect(action: "show", id: uploadedFileInstance.id)
        }
        else {
            render(view: "create", model: [uploadedFileInstance: uploadedFileInstance])
        }
    }

    def show = {
        def uploadedFileInstance = UploadedFile.get(params.id)
        if (!uploadedFileInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'uploadedFile.label', default: 'UploadedFile'), params.id])}"
            redirect(action: "list")
        }
        else {
            [uploadedFileInstance: uploadedFileInstance]
        }
    }

    def edit = {
        def uploadedFileInstance = UploadedFile.get(params.id)
        if (!uploadedFileInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'uploadedFile.label', default: 'UploadedFile'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [uploadedFileInstance: uploadedFileInstance]
        }
    }

    def update = {
        def uploadedFileInstance = UploadedFile.get(params.id)
        if (uploadedFileInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (uploadedFileInstance.version > version) {
                    
                    uploadedFileInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'uploadedFile.label', default: 'UploadedFile')] as Object[], "Another user has updated this UploadedFile while you were editing")
                    render(view: "edit", model: [uploadedFileInstance: uploadedFileInstance])
                    return
                }
            }
            uploadedFileInstance.properties = params
            if (!uploadedFileInstance.hasErrors() && uploadedFileInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'uploadedFile.label', default: 'UploadedFile'), uploadedFileInstance.id])}"
                redirect(action: "show", id: uploadedFileInstance.id)
            }
            else {
                render(view: "edit", model: [uploadedFileInstance: uploadedFileInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'uploadedFile.label', default: 'UploadedFile'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def uploadedFileInstance = UploadedFile.get(params.id)
        if (uploadedFileInstance) {
            try {
                uploadedFileInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'uploadedFile.label', default: 'UploadedFile'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'uploadedFile.label', default: 'UploadedFile'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'uploadedFile.label', default: 'UploadedFile'), params.id])}"
            redirect(action: "list")
        }
    }
}
