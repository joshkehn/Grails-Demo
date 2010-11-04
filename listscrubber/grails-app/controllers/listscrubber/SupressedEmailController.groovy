package listscrubber

class SupressedEmailController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [supressedEmailInstanceList: SupressedEmail.list(params), supressedEmailInstanceTotal: SupressedEmail.count()]
    }

    def create = {
        def supressedEmailInstance = new SupressedEmail()
        supressedEmailInstance.properties = params
        return [supressedEmailInstance: supressedEmailInstance]
    }

    def save = {
        def supressedEmailInstance = new SupressedEmail(params)
        if (supressedEmailInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'supressedEmail.label', default: 'SupressedEmail'), supressedEmailInstance.id])}"
            redirect(action: "show", id: supressedEmailInstance.id)
        }
        else {
            render(view: "create", model: [supressedEmailInstance: supressedEmailInstance])
        }
    }

    def show = {
        def supressedEmailInstance = SupressedEmail.get(params.id)
        if (!supressedEmailInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'supressedEmail.label', default: 'SupressedEmail'), params.id])}"
            redirect(action: "list")
        }
        else {
            [supressedEmailInstance: supressedEmailInstance]
        }
    }

    def edit = {
        def supressedEmailInstance = SupressedEmail.get(params.id)
        if (!supressedEmailInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'supressedEmail.label', default: 'SupressedEmail'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [supressedEmailInstance: supressedEmailInstance]
        }
    }

    def update = {
        def supressedEmailInstance = SupressedEmail.get(params.id)
        if (supressedEmailInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (supressedEmailInstance.version > version) {
                    
                    supressedEmailInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'supressedEmail.label', default: 'SupressedEmail')] as Object[], "Another user has updated this SupressedEmail while you were editing")
                    render(view: "edit", model: [supressedEmailInstance: supressedEmailInstance])
                    return
                }
            }
            supressedEmailInstance.properties = params
            if (!supressedEmailInstance.hasErrors() && supressedEmailInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'supressedEmail.label', default: 'SupressedEmail'), supressedEmailInstance.id])}"
                redirect(action: "show", id: supressedEmailInstance.id)
            }
            else {
                render(view: "edit", model: [supressedEmailInstance: supressedEmailInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'supressedEmail.label', default: 'SupressedEmail'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def supressedEmailInstance = SupressedEmail.get(params.id)
        if (supressedEmailInstance) {
            try {
                supressedEmailInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'supressedEmail.label', default: 'SupressedEmail'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'supressedEmail.label', default: 'SupressedEmail'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'supressedEmail.label', default: 'SupressedEmail'), params.id])}"
            redirect(action: "list")
        }
    }
}
