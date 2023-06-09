const { Sequelize, Model } = require('sequelize');

// Option 3: Passing parameters separately (other dialects)
// '0339930201An.'
const sequelize = new Sequelize('clinic_hcmute', 'root', '0339930201An.', {
  host: 'localhost',
  dialect: 'mysql',
  logging: false
});

let connectDB = async () => {
  console.log("connect")
  try {
    await sequelize.authenticate();
    console.log('Connection has been established successfully.');
  }
  catch (error) {
    console.error('Unable to connect to the database:', error);
  }
}

module.exports = connectDB