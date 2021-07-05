//функция получение индекса объекта в массиве
function getIndex(list, id) {
    for(var i = 0; i < list.length; i++) {
        if(list[i].id === id)
            return i;
    }
    return -1;
}

var employeeApi = Vue.resource('/api/employees{/id}');

Vue.component('employees-list', {
    props: {
        employees: Array
    },
    data: function() {
        return {
            //данные поля понадобятся нам при редактировании employee
            id: 0,
            firstName: '',
            lastName: '',
            departmentId: '',
            jobTitle: '',
            dateOfBirth: '',
            gender: ''
        }
    },
    template:
        '<div style="position: relative; width: 350px;">' +
        '<div class="information">' +
        '<input type="text" placeholder="Name" v-model="firstName"/><br>' +
        '<input type="text" placeholder="Surname" v-model="lastName"/><br>' +
        '<input type="text" placeholder="Department id" v-model="departmentId"/><br>' +
        '<input type="text" placeholder="Job title" v-model="jobTitle"/><br>' +
        '<input type="text" placeholder="Date of birth [YYYY-MM-DD]" v-model="dateOfBirth"/><br>' +
        '<select class="form-input-select" placeholder="Gender" v-model="gender" required>\n' +
        '    <option value="MALE" selected>MALE</option>\n' +
        '    <option value="FEMALE">FEMALE</option>\n' +
        '   </select><br>' +
        '<input type="button" class="double-border-button" value="Save" @click="save"/>' +
        '</div>' +
        '<table id="employeeTable" class="table_dark" width="100%">\n' +
        '  <tbody>\n' +
        '    <tr>' +
        '       <th align="center">id</th>' +
        '       <th align="center">Name</th>' +
        '       <th align="center">Surname</th>' +
        '       <th nowrap="true" align="center">Department id</th>' +
        '       <th align="center">Job title</th>' +
        '       <th align="center">Date of birth</th>' +
        '       <th align="center">Gender</th>' +
        '       <th align="center">Edit</th>' +
        '       <th align="center">Delete</th>' +
        '   </tr>' +
        '    <tr v-for="employee in employees">\n' +
        '      <td align="center"> {{ employee.id }}</td>\n' +
        '      <td align="center"> {{ employee.firstName }}</td>\n' +
        '      <td align="center"> {{ employee.lastName }}</td>\n' +
        '      <td align="center"> {{ employee.departmentId }}</td>\n' +
        '      <td align="center"> {{ employee.jobTitle }}</td>\n' +
        '      <td align="center"> {{ employee.dateOfBirth }}</td>\n' +
        '      <td align="center"> {{ employee.gender }}</td>\n' +
        '      <td align="center"> <input type="button" class="double-border-button" value="Edit" @click="edit(employee)"/> </td>\n' +
        '      <td align="center"> <input type="button" class="double-border-button" value="X" @click="del(employee)" /></td>\n' +
        '    </tr>\n' +
        '  </tbody>\n' +
        '</table>' +
        '</div>',
    methods: {
        save: function() {
            if(this.id == 0) {
                employeeApi.save({}, { firstName: this.firstName, lastName: this.lastName, departmentId: this.departmentId, jobTitle: this.jobTitle, dateOfBirth: this.dateOfBirth, gender: this.gender }).then(result =>
                    result.json().then(data => {
                        data.dateOfBirth = data.dateOfBirth.substring(0, 10);
                        this.employees.push(data);
                        this.id = 0,
                        this.firstName = '';
                        this.lastName = '';
                        this.departmentId ='';
                        this.jobTitle = '';
                        this.dateOfBirth = '';
                        this.gender = '';
                    })
                );
            } else {
                employeeApi.update({ id: this.id }, { id: this.id, firstName: this.firstName, lastName: this.lastName, departmentId: this.departmentId, jobTitle: this.jobTitle, dateOfBirth: this.dateOfBirth, gender: this.gender }).then(result =>
                    result.json().then(data => {
                        //в data нам вернулся изменённый объект employee, находим его индекс в массиве employees
                        var index = getIndex(this.employees, this.id);
                        //старый employee удаляем из employees, а на его место вставляем новый объект с сервера
                        data.dateOfBirth = data.dateOfBirth.substring(0, 10);
                        this.employees.splice(index, 1, data);
                        this.firstName = '';
                        this.lastName = '';
                        this.departmentId = '';
                        this.jobTitle = '';
                        this.dateOfBirth = '';
                        this.gender = '';
                        this.id = 0;
                    })
                );
            }
        },
        edit: function(employee) {
            //в методе edit мы просто заполняем поля опции data из переданного объекта message
            //поле message_text сразу будет отображено в элементе EditText
            this.id = employee.id;
            this.firstName = employee.firstName;
            this.lastName = employee.lastName;
            this.departmentId = employee.departmentId;
            this.jobTitle = employee.jobTitle;
            this.dateOfBirth = employee.dateOfBirth.substring(0,10);
            this.gender = employee.gender;
        },
        del: function(employee) {
            //здесь нам нужен id из объекта message, а не из data,
            // в ответе сервера проверяем метод ok, должен быть равен true
            employeeApi.remove({ id: employee.id }).then(result => {
                if(result.ok) {
                    this.employees.splice(this.employees.indexOf(employee), 1);
                    this.id = 0;
                    this.firstName = '';
                    this.lastName = '';
                    this.departmentId = '';
                    this.jobTitle = '';
                    this.dateOfBirth = '';
                    this.gender = '';
                }
            });
        }
    },
    //Здесь получили все записи с сервера и сохранили в массиве messages
    created: function() {
        employeeApi.get().then(result =>
            result.json().then(data =>
                data.forEach(employee => {
                    employee.dateOfBirth = employee.dateOfBirth.substring(0, 10);
                    this.employees.push(employee)
                })
            )
        );
    }
});

var app = new Vue({
    el: '#app',
    template: '<employees-list :employees="employees" />',
    data: {
        employees: []
    }
});