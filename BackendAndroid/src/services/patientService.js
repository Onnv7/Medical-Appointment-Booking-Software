import { reject } from "lodash";
import db from "../models";
let bcrypt = require('bcryptjs');
var salt = bcrypt.genSaltSync(10);



let hashUserPassword = (password) =>
{
    return new Promise(async (resolve, reject) => {
        try {
            var hashPassword = await bcrypt.hashSync(password, salt);
            // resolve in ket qua ==> tuong tu nhu console log
            resolve(hashPassword) 
        } catch (error) {
            reject(error)
        }
    })
}


let handleSinupService = (inputData) => {
    return new Promise( async(resolve, reject) => {
        try {
            console.log(inputData)
            let check = await checkEmail(inputData.email)

            if (check == true)
            {
                resolve({
                    errCode: 1,
                    errMessage: 'Email đã tồn tại !!!'
                })
            }
            else{
                let hashPasswordFromBcrypt = await hashUserPassword(inputData.password)
                await db.Patient.create({
                    name: inputData.name,
                    email: inputData.email,
                    password: hashPasswordFromBcrypt,
                })

                resolve({
                    errCode: 0,
                    errMessage: 'OK'
                })
            }
        } catch (error) {
            reject(error)
        }
    })
}

let checkEmail = (patientEmail) => {
    return new Promise (async (resolve, reject) => 
    {
        try {
            let data = await db.Patient.findOne({
                where: {
                    email: patientEmail
                } 
            })

            if (data) {
                resolve(true)
            }
            else
            {
                resolve(false)
            }
        } catch (error) {
            reject(error)
        }
    })
}

let handleLoginService = (email, password) => {
    return new Promise(async(resolve, reject) =>{
        try {
            let patientData = {}
            let isExit = await checkEmail(email)
            if(isExit)
            {
                let user =await db.Patient.findOne({
                    attributes:  ['email', 'password', 'name', 'gender', 'birth_date', 'avatar', 'phone', 'address'],
                    where: {
                        email: email
                    },
                    raw: true
                })

                if (user)
                {
                    let check = await bcrypt.compareSync(password, user.password);
                    if (check)
                    {
                        patientData.errCode = 0
                        patientData.errMessage = 'Đăng nhập thành công'
                        delete user.password
                        patientData.user = user
                    }
                    else
                    {
                        patientData.errCode = 3
                        patientData.errMessage = 'Password không chính xác'
                    }
                }
              
                else
                {
                    patientData.errCode = 2
                    patientData.errMessage = 'User không tìm thấy'
                }
            }
            else
            {
                patientData.errCode = 1
                patientData.errMessage = 'Không tìm thấy Email'
            }

            resolve(patientData)
        } catch (error) {
            reject(error)
        }
    })
}


let updateDatePatientService = (data) =>
{
    return new Promise(async (resolve,reject) => {
        try {
            if (!data.id)
            {
                resolve({
                    errCode:2,
                    errmessage: 'Phải điền đầy đủ thông tin'
                })
            }
            let user = await db.Patient.findOne({
                where: {
                    id : data.id
                },raw: false
                
            })
            if (user)
            {
                user.name = data.name,
                user.gender = data.gender,
                user.birth_date= data.birth_date,
                user.avatar = data.avatar,
                user.phone = data.phone,
                user.address =data.address
             
                await user.save()
                resolve({
                    errCode:0,
                    errMessage: 'Update người dùng thành công'
                })
            }
            else
            {
                resolve({
                    errCode: 1,
                    errmessage: 'Không tìm thấy người dùng'
                })
            }
        } catch (error) {
            reject(error)
        }
    })
}

let getTestService = (id) =>
{
    return new Promise(async (resolve,reject) => {
        try {
            let data = await db.Booking.findOne({
                where: {
                    user_id:id
                },
                // lấy thông tin trong bảng user và thấy thông tin của nó trong bảng Markdown
                include: [
                    {
                        model: db.Patient, attributes: ['email', 'name']
                    },
                    {
                        model: db.Doctor, attributes: ['phone', 'avatar']
                    },
                ],
                
                raw: false,
                // nest gôm nhóm lại
                nest: true
            })


            resolve({
                errCode: 0,
                data: data
            })

        } catch (error) {
            reject(error)
        }
    })
}

module.exports = {
    handleSinupService: handleSinupService,
    handleLoginService: handleLoginService, 
    updateDatePatientService: updateDatePatientService,
    getTestService:getTestService
}