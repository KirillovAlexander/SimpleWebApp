//функция получение индекса объекта в массиве
function getIndex(list, id) {
    for(var i = 0; i < list.lenght; ++i) {
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
        '      <td align="center"> {{ employee.gender }}</td>\n' +
        '      <td align="center"> <input type="button" class="double-border-button" value="Edit" @click="edit(employee)"/> </td>\n' +
        '      <td align="center"> <input type="button" class="double-border-button" value="X" @click="del(employee)" /></td>\n' +
        '    </tr>\n' +
        '  </tbody>\n' +
        '</table>' +
        '</div>',
    methods: {
        save: function() {
            //так как мы находимся в методе редактирования message, то необходимо брать его текущие поля,
            //а они у нас находятся в опции data данного компонента
            //если в элемент EditText не помещён message для редактирования, то поле message_id = 0
            if(this.id == 0) {
                //в методе save() передаём только поле message_text, т.к. поле id, будет изменено
                //на стороне сервера и в ответе мы уже получим полноценный message с уcтановленным id
                employeeApi.save({}, { firstName: this.firstName, lastName: this.lastName, departmentId: this.departmentId, jobTitle: this.jobTitle, gender: this.gender }).then(result =>
                    result.json().then(data => {
                        this.employees.push(data);
                        this.firstName = '';
                        this.lastName = '';
                        this.departmentId ='';
                        this.jobTitle = '';
                        this.gender = '';
                    })
                );
            } else {
                //в параметре метода update() нам снова не нужен целый объект message, используем только поле
                // message_text, поле id будет установлено на сервере из первого параметра update()
                employeeApi.update({}, { id: this.id, firstName: this.firstName, lastName: this.lastName, departmentId: this.departmentId, jobTitle: this.jobTitle, gender: this.gender }).then(result =>
                    result.json().then(data => {
                        //в data нам вернулся изменённый объект message, находим его индекс в массиве messages
                        var index = getIndex(this.employees, data.id);
                        //старый message удаляем из messages, а на его место вставляем новый объект с сервера
                        this.employees.splice(index, 1, data);
                        this.firstName = '';
                        this.lastName = '';
                        this.departmentId = '';
                        this.jobTitle = '';
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
            this.gender = employee.gender;
        },
        del: function(employee) {
            //здесь нам нужен id из объекта message, а не из data,
            // в ответе сервера проверяем метод ok, должен быть равен true
            employeeApi.remove({ id: employee.id }).then(result => {
                if(result.ok) {
                    this.employees.splice(this.employees.indexOf(employee), 1);
                    //также очищаем поля message_id и message_text,
                    // т.к. message может уже быть помещён в поле редактирования
                    this.id = 0;
                    this.firstName = '';
                    this.lastName = '';
                    this.departmentId = '';
                    this.jobTitle = '';
                    this.gender = '';
                }
            });
        }
    },
    //Здесь получили все записи с сервера и сохранили в массиве messages
    created: function() {
        employeeApi.get().then(result =>
            result.json().then(data =>
                data.forEach(employee => this.employees.push(employee))
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