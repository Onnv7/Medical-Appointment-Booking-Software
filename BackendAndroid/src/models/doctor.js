'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Doctor extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
        Doctor.hasMany(models.Schedule, {foreignKey: 'doctor_id'})
        Doctor.hasOne(models.Specialist, {foreignKey: 'specialist_id'})
        Doctor.hasMany(models.Booking, {foreignKey: 'doctor_id'})
     }
  }
  Doctor.init({
    email: DataTypes.STRING,
    password: DataTypes.STRING,
    name: DataTypes.STRING,
    gender: DataTypes.STRING,
    birth_date: DataTypes.DATE,
    avatar: DataTypes.STRING,
    phone: DataTypes.STRING,
    introduction: DataTypes.STRING,
    clinic_name: DataTypes.STRING,
    clinic_address: DataTypes.STRING,
    price: DataTypes.FLOAT,
    specialist_id: DataTypes.STRING,
  }, {
    sequelize,
    modelName: 'Doctor',
  });
  return Doctor;
};