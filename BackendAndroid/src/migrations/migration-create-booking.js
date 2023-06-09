'use strict';
/** @type {import('sequelize-cli').Migration} */
module.exports = {
    async up(queryInterface, Sequelize) {
        await queryInterface.createTable('bookings', {
            id: {
                allowNull: false,
                autoIncrement: true,
                primaryKey: true,
                type: Sequelize.INTEGER
            },
            user_id:
            {
                type: Sequelize.INTEGER
            },
            doctor_id:
            {
                type: Sequelize.INTEGER
            },
            reminder: {
                type: Sequelize.STRING
            },
            status: {
                type: Sequelize.STRING
            },
            advice: {
                type: Sequelize.STRING
            },
            review: {
                type: Sequelize.STRING
            },
            start: {
                type: Sequelize.INTEGER
            },
            createdAt:
            {
                allowNull: false,
                type: Sequelize.DATE
            },
            updatedAt:
            {
                allowNull: false,
                type: Sequelize.DATE
            }
        });
    },
    async down(queryInterface, Sequelize) {
        await queryInterface.dropTable('bookings');
    }
};