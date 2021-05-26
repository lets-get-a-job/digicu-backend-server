const express = require('express')

const app = express()
const port = 5000

app.use(express.static('client-test'))

app.listen(port, () => {
  console.log(
    `check http://localhost:${port}/POS.html and http://localhost:${port}/QR.html`,
  )
})
