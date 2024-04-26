console.log("IMPORTED JSON FORM")

let prevSchema = {}
export let removedFields = new Set()

// ADD FIELD
function addField(name, type, value) {
    let jsonFields = document.getElementById("json-form-fields")

    appendChildren(jsonFields, [createJsonFieldElement(name, type, value)])
}

function createJsonFieldElement(name, type, value) {
    let uuid = crypto.randomUUID()

    let fieldTableRow = createElement('tr', uuid, ['json-field'])

    let fieldNameInput = createElement('input', 'json-field-name-' + uuid, ['json-field-name'])
    if (name) fieldNameInput.value = name

    let fieldTypeSelect = createElement('select', 'json-field-type-' + uuid, ['json-field-type'])

    let intOption = createElement('option', 'json-field-type-int-' + uuid, [])
    intOption.innerText = 'Integer'
    intOption.value = 'Integer'
    if (type === 'Integer') {
        intOption.selected = true
    }

    let strOption = createElement('option', 'json-field-type-str-' + uuid, [])
    strOption.innerText = 'Text'
    strOption.value = 'String'
    if (type === 'String') {
        strOption.selected = true
    }

    let doubleOption = createElement('option', 'json-field-type-double-' + uuid, [])
    doubleOption.innerText = 'Decimal'
    doubleOption.value = 'Double'
    if (type === 'Double') {
        doubleOption.selected = true
    }

    if (type === undefined) {
        strOption.selected = true
    }

    appendChildren(fieldTypeSelect, [strOption, intOption, doubleOption])

    // let fieldValuenput = createElement('input', 'json-field-value-' + uuid, ['json-field-value'])
    // if (value) {
    //     fieldValuenput.value = value
    // }

    let removeButton = createElement('button', 'remove-' + uuid, ['json-remove-field'])
    removeButton.innerText = 'Remove'
    removeButton.onclick = function () {
        // if the key is present in prev schema, add to remove list
        if (name && Object.keys(prevSchema).find(key => key === name) !== undefined) {
            removedFields.add(name)
        }

        removeField(uuid)
    }

    appendChildren(fieldTableRow, [
        fieldNameInput,
        fieldTypeSelect,
        // fieldValuenput,
        removeButton].map(wrapInTd))

    return fieldTableRow
}


function appendChildren(parent, children) {
    for (let i = 0; i < children.length; i++) {
        parent.appendChild(children[i])
    }
}

function wrapInTd(element) {
    let td = document.createElement('td')
    td.appendChild(element)

    return td
}

function createElement(htmlTag, id, classList) {
    let el = document.createElement(htmlTag)
    el.id = id
    el.classList.add(...classList)

    return el
}


document.getElementById("add-field").onclick = function () {
    addField()
}

// REMOVE FIELD
function removeField(uuid) {
    document.getElementById(uuid).remove()
}

// GENERATE JSON
function generateJson() {
    console.log("GENERATING JSON")
    let trs = document.getElementById("json-form-fields").querySelectorAll('tr')

    let jsonAcc = {}

    for (let i = 0; i < trs.length; i++) {
        const tr = trs[i];

        let fieldName = tr.querySelector("#json-field-name-" + tr.id).value

        let fieldType = tr.querySelector("#json-field-type-" + tr.id).value

        // if the key is not present in prev schema its an add
        // else if the key is present and the field type is different, its an remove and add
        // else the key is present in prev schema and the same type, nothing changed
        if (Object.keys(prevSchema).find(key => key === fieldName) === undefined) {
            jsonAcc[`${fieldName}`] = fieldType
        } else if (prevSchema[fieldName] !== fieldType) {
            removedFields.add(fieldName)
            jsonAcc[fieldName] = fieldType
        } else {
            continue
        }
    }

    console.log({ jsonAcc, removedFields })
}

document.getElementById("save-changes").onclick = function () {
    generateJson()
}

// READ CAMPAIGN DATA
// let typeMap = {
//     'string': function (str) { return 'str' },
//     'number': function (n) {
//         return Number(n) % 1 == 0 ? 'int' : 'float'
//     }
// }

export function readCampaign(campaignSchemaStr) {
    console.log("READING CAMPAIGN")

    let campaignSchema = campaignSchemaStr
        .replaceAll("}", "")
        .replaceAll("{", "")
        .split(",")
        .map(l => l.split("=").map(c => c.trim()))

    console.log({ campaignSchema })

    // let jsonInput = JSON.parse(document.getElementById('json-io').value)

    campaignSchema.forEach(line => {
        let key = line[0]
        let type = line[1]
        prevSchema[key] = type
        addField(key, type)
    });

}

// document.getElementById("read-json").onclick = readJson
