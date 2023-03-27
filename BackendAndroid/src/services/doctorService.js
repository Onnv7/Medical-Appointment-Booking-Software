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

let checkEmail = (doctorEmail) => {
    return new Promise (async (resolve, reject) => 
    {
        try {
            let data = await db.Doctor.findOne({
                where: {
                    email: doctorEmail
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

let handleSinupDoctorService = (inputData) => {
    return new Promise( async(resolve, reject) => {
        try {
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
                await db.Doctor.create({
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

module.exports = {
    handleSinupDoctorService: handleSinupDoctorService
}
